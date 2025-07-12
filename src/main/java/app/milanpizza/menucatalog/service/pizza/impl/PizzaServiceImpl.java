package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.pizza.*;
import app.milanpizza.menucatalog.dto.request.pizza.*;
import app.milanpizza.menucatalog.dto.response.pizza.*;
import app.milanpizza.menucatalog.exception.*;
import app.milanpizza.menucatalog.mapper.*;
import app.milanpizza.menucatalog.repository.*;
import app.milanpizza.menucatalog.repository.pizza.*;
import app.milanpizza.menucatalog.service.pizza.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    private final MenuRepository menuRepository;
    private final PizzaRepository pizzaRepository;
    private final PizzaBaseRepository pizzaBaseRepository;
    private final PizzaSizeRepository pizzaSizeRepository;
    private final AvailableSizeRepository availableSizeRepository;
    private final ToppingRepository toppingRepository;
    private final AllowedToppingRepository allowedToppingRepository;

    private final PizzaMapper pizzaMapper;
    private final PizzaBaseMapper pizzaBaseMapper;
    private final PizzaSizeMapper pizzaSizeMapper;
    private final AvailableSizeMapper availableSizeMapper;
    private final ToppingMapper toppingMapper;
    private final AllowedToppingMapper allowedToppingMapper;

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", allEntries = true)
    public PizzaDetailedResponse createPizza(CreatePizzaRequest request) {
        validateMenuExists(request.getMenuId());

        Pizza pizza = pizzaMapper.toEntity(request);
        Pizza savedPizza = pizzaRepository.save(pizza);

        log.info("Created new pizza with ID: {}", savedPizza.getPizzaId());
        return buildDetailedPizzaResponse(savedPizza);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "pizzas", key = "#id")
    public PizzaDetailedResponse getPizzaById(String id) {
        log.debug("Fetching pizza with ID: {}", id);
        Pizza pizza = findPizzaById(id);
        return buildDetailedPizzaResponse(pizza);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaSummaryResponse> getAllPizzasByMenu(String menuId) {
        validateMenuExists(menuId);
        return pizzaRepository.findByMenuId(menuId).stream()
                .map(pizzaMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaSummaryResponse> getAvailablePizzasByMenu(String menuId) {
        validateMenuExists(menuId);
        return pizzaRepository.findByMenuIdAndIsAvailable(menuId, true).stream()
                .map(pizzaMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", key = "#id")
    public PizzaDetailedResponse updatePizza(String id, UpdatePizzaRequest request) {
        Pizza existingPizza = findPizzaById(id);
        pizzaMapper.updateEntity(request, existingPizza);

        Pizza updatedPizza = pizzaRepository.save(existingPizza);
        log.info("Updated pizza with ID: {}", id);
        return buildDetailedPizzaResponse(updatedPizza);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", key = "#id")
    public void togglePizzaAvailability(String id) {
        Pizza pizza = findPizzaById(id);
        pizza.setIsAvailable(!pizza.getIsAvailable());
        pizzaRepository.save(pizza);
        log.info("Toggled availability for pizza ID: {} to {}", id, pizza.getIsAvailable());
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", key = "#request.pizzaId")
    public void addSizeToPizza(PizzaSizeConfigRequest request) {
        validatePizzaAndSizeExist(request.getPizzaId(), request.getSizeId());

        if (availableSizeRepository.existsByPizzaIdAndSizeId(request.getPizzaId(), request.getSizeId())) {
            throw new DuplicateResourceException("Size already configured for this pizza");
        }

        availableSizeRepository.save(availableSizeMapper.toEntity(request));
        log.info("Added size {} to pizza {}", request.getSizeId(), request.getPizzaId());
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", key = "#request.pizzaId")
    public void addToppingToPizza(PizzaToppingConfigRequest request) {
        validatePizzaAndToppingExist(request.getPizzaId(), request.getToppingId());

        if (allowedToppingRepository.existsByPizzaIdAndToppingId(request.getPizzaId(), request.getToppingId())) {
            throw new DuplicateResourceException("Topping already configured for this pizza");
        }

        allowedToppingRepository.save(allowedToppingMapper.toEntity(request));
        log.info("Added topping {} to pizza {}", request.getToppingId(), request.getPizzaId());
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", key = "#pizzaId")
    public void removeToppingFromPizza(String pizzaId, String toppingId) {
        if (!allowedToppingRepository.existsByPizzaIdAndToppingId(pizzaId, toppingId)) {
            throw new ResourceNotFoundException("Topping configuration not found");
        }

        allowedToppingRepository.deleteByPizzaIdAndToppingId(pizzaId, toppingId);
        log.info("Removed topping {} from pizza {}", toppingId, pizzaId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzas", key = "#id")
    public void deletePizza(String id) {
        if (!pizzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza not found with id: " + id);
        }

        pizzaRepository.deleteById(id);
        log.info("Deleted pizza with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private PizzaDetailedResponse buildDetailedPizzaResponse(Pizza pizza) {
        PizzaDetailedResponse response = pizzaMapper.toDetailedResponse(pizza);
        response.setPizzaBases(getAllPizzaBases());
        response.setAvailableSizes(getAvailableSizesForPizza(pizza.getPizzaId()));
        response.setAllowedToppings(getAllowedToppingsForPizza(pizza.getPizzaId()));
        return response;
    }

    @Cacheable("pizzaBases")
    private List<PizzaBaseResponse> getAllPizzaBases() {
        return pizzaBaseRepository.findAll().stream()
                .map(pizzaBaseMapper::toResponse)
                .toList();
    }

    private List<AvailableSizeResponse> getAvailableSizesForPizza(String pizzaId) {
        return availableSizeRepository.findByPizzaId(pizzaId).stream()
                .map(this::enrichSizeResponse)
                .toList();
    }

    private AvailableSizeResponse enrichSizeResponse(AvailableSize availableSize) {
        AvailableSizeResponse response = availableSizeMapper.toAvailableSizeResponse(availableSize);
        response.setSizeName(getPizzaSizeName(availableSize.getSizeId()));
        return response;
    }

    private List<AllowedToppingResponse> getAllowedToppingsForPizza(String pizzaId) {
        return allowedToppingRepository.findByPizzaId(pizzaId).stream()
                .map(this::enrichToppingResponse)
                .toList();
    }

    private AllowedToppingResponse enrichToppingResponse(AllowedTopping allowedTopping) {
        AllowedToppingResponse response = allowedToppingMapper.toAllowedToppingResponse(allowedTopping);
        response.setToppingName(getToppingName(allowedTopping.getToppingId()));
        return response;
    }

    private String getPizzaSizeName(String sizeId) {
        return pizzaSizeRepository.findById(sizeId)
                .map(PizzaSize::getName)
                .map(Enum::toString)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza size not found"));
    }

    private String getToppingName(String toppingId) {
        return toppingRepository.findById(toppingId)
                .map(Topping::getName)
                .orElseThrow(() -> new ResourceNotFoundException("Topping not found"));
    }

    private Pizza findPizzaById(String id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found with id: " + id));
    }

    private void validateMenuExists(String menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new ResourceNotFoundException("Menu not found with id: " + menuId);
        }
    }

    private void validatePizzaAndSizeExist(String pizzaId, String sizeId) {
        if (!pizzaRepository.existsById(pizzaId)) {
            throw new ResourceNotFoundException("Pizza not found with id: " + pizzaId);
        }
        if (!pizzaSizeRepository.existsById(sizeId)) {
            throw new ResourceNotFoundException("Pizza size not found with id: " + sizeId);
        }
    }

    private void validatePizzaAndToppingExist(String pizzaId, String toppingId) {
        if (!pizzaRepository.existsById(pizzaId)) {
            throw new ResourceNotFoundException("Pizza not found with id: " + pizzaId);
        }
        if (!toppingRepository.existsById(toppingId)) {
            throw new ResourceNotFoundException("Topping not found with id: " + toppingId);
        }
    }
}