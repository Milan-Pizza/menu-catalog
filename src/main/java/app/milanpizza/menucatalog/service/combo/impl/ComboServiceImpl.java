package app.milanpizza.menucatalog.service.combo.impl;

import app.milanpizza.menucatalog.domain.combo.ComboItem;
import app.milanpizza.menucatalog.domain.combo.ComboMeal;
import app.milanpizza.menucatalog.domain.enums.ItemType;
import app.milanpizza.menucatalog.dto.request.combo.*;
import app.milanpizza.menucatalog.dto.response.combo.*;
import app.milanpizza.menucatalog.exception.*;
import app.milanpizza.menucatalog.mapper.*;
import app.milanpizza.menucatalog.repository.*;
import app.milanpizza.menucatalog.repository.combo.ComboItemRepository;
import app.milanpizza.menucatalog.repository.combo.ComboMealRepository;
import app.milanpizza.menucatalog.repository.item.DrinkRepository;
import app.milanpizza.menucatalog.repository.item.SideItemRepository;
import app.milanpizza.menucatalog.repository.pizza.PizzaRepository;
import app.milanpizza.menucatalog.service.combo.ComboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {

    private final MenuRepository menuRepository;
    private final PizzaRepository pizzaRepository;
    private final SideItemRepository sideItemRepository;
    private final DrinkRepository drinkRepository;
    private final ComboMealRepository comboMealRepository;
    private final ComboItemRepository comboItemRepository;

    private final ComboMapper comboMapper;
    private final ComboItemMapper comboItemMapper;

    @Override
    @Transactional
    @CacheEvict(value = {"combos", "activeCombos"}, key = "#request.menuId")
    public ComboDetailedResponse createCombo(CreateComboRequest request) {
        validateMenuExists(request.getMenuId());
        validateComboNameUnique(request.getName(), request.getMenuId());

        ComboMeal combo = comboMapper.toEntity(request);
        combo.setIsActive(true);
        ComboMeal savedCombo = comboMealRepository.save(combo);

        log.info("Created new combo with ID: {} for menu: {}", savedCombo.getComboId(), request.getMenuId());
        return buildDetailedComboResponse(savedCombo);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "combos", key = "#id")
    public ComboDetailedResponse getComboById(String id) {
        log.debug("Fetching combo with ID: {}", id);
        ComboMeal combo = findComboById(id);
        return buildDetailedComboResponse(combo);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "combos", key = "#menuId")
    public List<ComboSummaryResponse> getAllCombosByMenu(String menuId) {
        validateMenuExists(menuId);
        return comboMealRepository.findByMenuId(menuId).stream()
                .map(comboMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "activeCombos", key = "#menuId")
    public List<ComboSummaryResponse> getActiveCombosByMenu(String menuId) {
        validateMenuExists(menuId);
        return comboMealRepository.findActiveCombosByMenu(menuId).stream()
                .map(comboMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"combos", "activeCombos"}, key = "#result.menuId")
    public ComboDetailedResponse updateCombo(String id, UpdateComboRequest request) {
        ComboMeal existingCombo = findComboById(id);
        comboMapper.updateEntity(request, existingCombo);

        ComboMeal updatedCombo = comboMealRepository.save(existingCombo);
        log.info("Updated combo with ID: {}", id);
        return buildDetailedComboResponse(updatedCombo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "activeCombos", key = "#result.menuId")
    public void toggleComboAvailability(String id) {
        ComboMeal combo = findComboById(id);
        combo.setIsActive(!combo.getIsActive());

        comboMealRepository.save(combo);
        log.info("Toggled availability for combo ID: {} to {}", id, combo.getIsActive());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"combos", "activeCombos"}, key = "#result.menuId")
    public void deleteCombo(String id) {
        ComboMeal combo = findComboById(id);
        comboMealRepository.delete(combo);
        log.info("Deleted combo with ID: {}", id);
    }

    // === COMBO ITEM MANAGEMENT ===
    @Override
    @Transactional
    @CacheEvict(value = {"combos", "activeCombos"}, key = "#result.menuId")
    public ComboItemResponse addItemToCombo(String comboId, AddComboItemRequest request) {
        ComboMeal combo = findComboById(comboId);
        validateItemExists(request.getItemId(), request.getItemType());
        validateItemNotInCombo(comboId, request.getItemId(), request.getItemType());

        ComboItem item = comboItemMapper.toEntity(request);
        item.setComboId(comboId);
        ComboItem savedItem = comboItemRepository.save(item);

        log.info("Added item {} of type {} to combo {}",
                request.getItemId(), request.getItemType(), comboId);
        return comboItemMapper.toResponse(savedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemResponse> getComboItems(String comboId) {
        findComboById(comboId); // Validate combo exists
        return comboItemRepository.findByComboId(comboId).stream()
                .map(comboItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"combos", "activeCombos"}, key = "#result.menuId")
    public ComboItemResponse updateComboItem(String comboId, String itemId, UpdateComboItemRequest request) {
        ComboItem item = findComboItem(comboId, itemId);
        comboItemMapper.updateEntity(request, item);

        ComboItem updatedItem = comboItemRepository.save(item);
        log.info("Updated item {} in combo {}", itemId, comboId);
        return comboItemMapper.toResponse(updatedItem);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"combos", "activeCombos"}, key = "#result.menuId")
    public void removeItemFromCombo(String comboId, String itemId) {
        ComboItem item = findComboItem(comboId, itemId);
        validateItemNotRequired(item);

        comboItemRepository.delete(item);
        log.info("Removed item {} from combo {}", itemId, comboId);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private ComboDetailedResponse buildDetailedComboResponse(ComboMeal combo) {
        ComboDetailedResponse response = comboMapper.toDetailedResponse(combo);
        response.setItems(getComboItems(combo.getComboId()));
        return response;
    }

    private ComboMeal findComboById(String id) {
        return comboMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));
    }

    private ComboItem findComboItem(String comboId, String itemId) {
        return (ComboItem) comboItemRepository.findByComboIdAndItemId(comboId, itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Combo item not found with id: " + itemId + " in combo: " + comboId));
    }

    private void validateMenuExists(String menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new ResourceNotFoundException("Menu not found with id: " + menuId);
        }
    }

    private void validateComboNameUnique(String name, String menuId) {
        if (comboMealRepository.existsByNameAndMenuId(name, menuId)) {
            throw new DuplicateResourceException(
                    "Combo with name '" + name + "' already exists in menu: " + menuId);
        }
    }

    private void validateItemExists(String itemId, ItemType itemType) {
        switch (itemType) {
            case PIZZA -> {
                if (!pizzaRepository.existsById(itemId)) {
                    throw new ResourceNotFoundException("Pizza not found with id: " + itemId);
                }
            }
            case SIDE -> {
                if (!sideItemRepository.existsById(itemId)) {
                    throw new ResourceNotFoundException("Side item not found with id: " + itemId);
                }
            }
            case DRINK -> {
                if (!drinkRepository.existsById(itemId)) {
                    throw new ResourceNotFoundException("Drink not found with id: " + itemId);
                }
            }
        }
    }

    private void validateItemNotInCombo(String comboId, String itemId, ItemType itemType) {
        if (comboItemRepository.existsByComboIdAndItemIdAndItemType(
                comboId, itemId, itemType.name())) {
            throw new DuplicateResourceException(
                    "Item already exists in this combo with type: " + itemType);
        }
    }

    private void validateItemNotRequired(ComboItem item) {
        if (item.getIsRequired()) {
            throw new InvalidOperationException("Cannot remove a required item from combo");
        }
    }
}