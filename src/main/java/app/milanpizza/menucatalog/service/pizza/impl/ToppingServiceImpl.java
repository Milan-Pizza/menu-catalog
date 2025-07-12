package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import app.milanpizza.menucatalog.domain.pizza.Topping;
import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.ToppingResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.InvalidCategoryException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.ToppingMapper;
import app.milanpizza.menucatalog.repository.pizza.ToppingRepository;
import app.milanpizza.menucatalog.service.pizza.ToppingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {

    private final ToppingRepository toppingRepository;
    private final ToppingMapper toppingMapper;

    @Override
    @Transactional
    @CacheEvict(value = {"toppings", "toppingsByCategory"}, allEntries = true)
    public ToppingResponse createTopping(ToppingRequest request) {
        validateToppingDoesNotExist(request.getName());
        validateCategory(request.getCategory().toString());

        Topping topping = toppingMapper.toEntity(request);
        Topping savedTopping = toppingRepository.save(topping);

        log.info("Created new topping with ID: {}", savedTopping.getToppingId());
        return toppingMapper.toToppingResponse(savedTopping);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "toppings", key = "#id")
    public ToppingResponse getToppingById(String id) {
        log.debug("Fetching topping with ID: {}", id);
        return toppingRepository.findById(id)
                .map(toppingMapper::toToppingResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Topping not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("toppings")
    public List<ToppingResponse> getAllToppings() {
        return toppingRepository.findAll().stream()
                .map(toppingMapper::toToppingResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "toppingsByCategory", key = "#category")
    public List<ToppingResponse> getToppingsByCategory(String category) {
        validateCategory(category);
        return toppingRepository.findByCategory(category).stream()
                .map(toppingMapper::toToppingResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"toppings", "toppingsByCategory"}, allEntries = true)
    public ToppingResponse updateTopping(String id, ToppingRequest request) {
        Topping existingTopping = findToppingById(id);
        validateCategory(request.getCategory().toString());

        toppingMapper.updateEntity(request, existingTopping);
        Topping updatedTopping = toppingRepository.save(existingTopping);

        log.info("Updated topping with ID: {}", id);
        return toppingMapper.toToppingResponse(updatedTopping);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"toppings", "toppingsByCategory"}, allEntries = true)
    public void deleteTopping(String id) {
        if (!toppingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topping not found with id: " + id);
        }

        toppingRepository.deleteById(id);
        log.info("Deleted topping with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private Topping findToppingById(String id) {
        return toppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topping not found with id: " + id));
    }

    private void validateToppingDoesNotExist(String name) {
        if (toppingRepository.existsByName(name)) {
            throw new DuplicateResourceException("Topping with name '" + name + "' already exists");
        }
    }

    private void validateCategory(String category) {
        try {
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            ToppingCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException("Invalid topping category. Valid categories are: " +
                    Arrays.stream(ToppingCategory.values())
                          .map(Enum::name)
                          .collect(Collectors.joining(", ")));
        }
    }
}