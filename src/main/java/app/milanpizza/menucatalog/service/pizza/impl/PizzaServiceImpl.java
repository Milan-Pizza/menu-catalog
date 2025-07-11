package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.pizza.Pizza;
import app.milanpizza.menucatalog.domain.pizza.AllowedTopping;
import app.milanpizza.menucatalog.domain.pizza.Topping;
import app.milanpizza.menucatalog.dto.request.pizza.CreatePizzaRequest;
import app.milanpizza.menucatalog.dto.request.pizza.PizzaToppingConfigRequest;
import app.milanpizza.menucatalog.dto.request.pizza.UpdatePizzaRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaDetailedResponse;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSummaryResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.PizzaMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.pizza.AllowedToppingRepository;
import app.milanpizza.menucatalog.repository.pizza.PizzaRepository;
import app.milanpizza.menucatalog.repository.pizza.ToppingRepository;
import app.milanpizza.menucatalog.service.pizza.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final MenuRepository menuRepository;
    private final ToppingRepository toppingRepository;
    private final AllowedToppingRepository allowedToppingRepository;
    private final PizzaMapper pizzaMapper;

    @Override
    @Transactional
    public PizzaDetailedResponse createPizza(CreatePizzaRequest request) {
        if (!menuRepository.existsById(request.getMenuId())) {
            throw new ResourceNotFoundException("Menu not found with id: " + request.getMenuId());
        }

        Pizza pizza = pizzaMapper.toEntity(request);
        Pizza savedPizza = pizzaRepository.save(pizza);
        return pizzaMapper.toDetailedResponse(savedPizza);
    }

    @Override
    @Transactional(readOnly = true)
    public PizzaDetailedResponse getPizzaById(String id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found with id: " + id));
        return pizzaMapper.toDetailedResponse(pizza);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaSummaryResponse> getAllPizzasByMenu(String menuId) {
        return pizzaRepository.findByMenuId(menuId).stream()
                .map(pizzaMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaSummaryResponse> getAvailablePizzasByMenu(String menuId) {
        return pizzaRepository.findByMenuIdAndIsAvailable(menuId, true).stream()
                .map(pizzaMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    public PizzaDetailedResponse updatePizza(String id, UpdatePizzaRequest request) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found with id: " + id));

        pizzaMapper.updateEntity(request, existingPizza);
        Pizza updatedPizza = pizzaRepository.save(existingPizza);
        return pizzaMapper.toDetailedResponse(updatedPizza);
    }

    @Override
    @Transactional
    public void togglePizzaAvailability(String id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found with id: " + id));
        pizza.setIsAvailable(!pizza.getIsAvailable());
        pizzaRepository.save(pizza);
    }

    @Override
    @Transactional
    public void addToppingToPizza(PizzaToppingConfigRequest request) {
        if (!pizzaRepository.existsById(request.getPizzaId())) {
            throw new ResourceNotFoundException("Pizza not found with id: " + request.getPizzaId());
        }
        if (!toppingRepository.existsById(request.getToppingId())) {
            throw new ResourceNotFoundException("Topping not found with id: " + request.getToppingId());
        }
        if (allowedToppingRepository.existsByPizzaIdAndToppingId(request.getPizzaId(), request.getToppingId())) {
            throw new IllegalArgumentException("This topping is already configured for the pizza");
        }

        AllowedTopping allowedTopping = AllowedTopping.builder()
                .pizzaId(request.getPizzaId())
                .toppingId(request.getToppingId())
                .baseAdditionalPrice(request.getBaseAdditionalPrice())
                .isDefault(request.getIsDefault())
                .isRecommended(request.getIsRecommended())
                .maxQuantity(request.getMaxQuantity())
                .build();

        allowedToppingRepository.save(allowedTopping);
    }

    @Override
    @Transactional
    public void removeToppingFromPizza(String pizzaId, String toppingId) {
        allowedToppingRepository.deleteByPizzaIdAndToppingId(pizzaId, toppingId);
    }

    @Override
    @Transactional
    public void deletePizza(String id) {
        if (!pizzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza not found with id: " + id);
        }
        pizzaRepository.deleteById(id);
    }
}