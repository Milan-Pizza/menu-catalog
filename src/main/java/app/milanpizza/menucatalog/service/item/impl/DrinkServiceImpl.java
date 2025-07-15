package app.milanpizza.menucatalog.service.item.impl;

import app.milanpizza.menucatalog.domain.enums.DrinkCategory;
import app.milanpizza.menucatalog.domain.enums.DrinkSize;
import app.milanpizza.menucatalog.domain.item.Drink;
import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.InvalidCategoryException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.DrinkMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.item.DrinkRepository;
import app.milanpizza.menucatalog.service.item.DrinkService;
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
public class DrinkServiceImpl implements DrinkService {

    private final DrinkRepository drinkRepository;
    private final MenuRepository menuRepository;
    private final DrinkMapper drinkMapper;

    @Override
    @Transactional
    @CacheEvict(value = {"drinks", "availableDrinks"}, key = "#request.menuId")
    public DrinkResponse createDrink(CreateDrinkRequest request) {
        validateMenuExists(request.getMenuId());
        validateDrinkNameUnique(request.getName(), request.getMenuId());
        validateDrinkCategory(String.valueOf(request.getCategory()));
        validateDrinkSize(String.valueOf(request.getSize()));

        Drink drink = drinkMapper.toEntity(request);
        Drink savedDrink = drinkRepository.save(drink);

        log.info("Created new drink with ID: {} for menu: {}", savedDrink.getDrinkId(), request.getMenuId());
        return drinkMapper.toResponse(savedDrink);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "drinks", key = "#id")
    public DrinkResponse getDrinkById(String id) {
        log.debug("Fetching drink with ID: {}", id);
        return drinkRepository.findById(id)
                .map(drinkMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "drinks", key = "#menuId")
    public List<DrinkResponse> getAllDrinksByMenu(String menuId) {
        validateMenuExists(menuId);
        return drinkRepository.findByMenuId(menuId).stream()
                .map(drinkMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "availableDrinks", key = "#menuId")
    public List<DrinkResponse> getAvailableDrinksByMenu(String menuId) {
        validateMenuExists(menuId);
        return drinkRepository.findByMenuIdAndIsAvailable(menuId, true).stream()
                .map(drinkMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"drinks", "availableDrinks"}, key = "#result.menuId")
    public DrinkResponse updateDrink(String id, UpdateDrinkRequest request) {
        Drink existingDrink = findDrinkById(id);
        if (request.getCategory() != null) validateDrinkCategory(String.valueOf(request.getCategory()));
        if (request.getSize() != null) validateDrinkSize(String.valueOf(request.getSize()));

        drinkMapper.updateEntity(request, existingDrink);
        Drink updatedDrink = drinkRepository.save(existingDrink);

        log.info("Updated drink with ID: {}", id);
        return drinkMapper.toResponse(updatedDrink);
    }

    @Override
    @Transactional
    @CacheEvict(value = "availableDrinks", key = "#result.menuId")
    public void toggleDrinkAvailability(String id) {
        Drink drink = findDrinkById(id);
        drink.setIsAvailable(!drink.getIsAvailable());

        drinkRepository.save(drink);
        log.info("Toggled availability for drink ID: {} to {}", id, drink.getIsAvailable());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"drinks", "availableDrinks"}, key = "#result.menuId")
    public void deleteDrink(String id) {
        Drink drink = findDrinkById(id);
        drinkRepository.deleteById(id);
        log.info("Deleted drink with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private Drink findDrinkById(String id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));
    }

    private void validateMenuExists(String menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new ResourceNotFoundException("Menu not found with id: " + menuId);
        }
    }

    private void validateDrinkNameUnique(String name, String menuId) {
        if (drinkRepository.existsByNameAndMenuId(name, menuId)) {
            throw new DuplicateResourceException(
                    "Drink with name '" + name + "' already exists in menu: " + menuId);
        }
    }

    private void validateDrinkCategory(String category) {
        try {
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            DrinkCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException("Invalid drink category. Valid categories are: " +
                    Arrays.stream(DrinkCategory.values())
                          .map(Enum::name)
                          .collect(Collectors.joining(", ")));
        }
    }

    private void validateDrinkSize(String size) {
        try {
            if (size == null) {
                throw new IllegalArgumentException("Size cannot be null");
            }
            DrinkSize.valueOf(size.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException("Invalid drink size. Valid sizes are: " +
                    Arrays.stream(DrinkSize.values())
                          .map(Enum::name)
                          .collect(Collectors.joining(", ")));
        }
    }
}