package app.milanpizza.menucatalog.service.impl;

import app.milanpizza.menucatalog.domain.Menu;
import app.milanpizza.menucatalog.dto.request.menu.*;
import app.milanpizza.menucatalog.dto.response.combo.ComboSummaryResponse;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.dto.response.menu.*;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSummaryResponse;
import app.milanpizza.menucatalog.exception.*;
import app.milanpizza.menucatalog.mapper.*;
import app.milanpizza.menucatalog.repository.*;
import app.milanpizza.menucatalog.repository.combo.ComboMealRepository;
import app.milanpizza.menucatalog.repository.item.DrinkRepository;
import app.milanpizza.menucatalog.repository.item.SideItemRepository;
import app.milanpizza.menucatalog.repository.pizza.PizzaRepository;
import app.milanpizza.menucatalog.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final PizzaRepository pizzaRepository;
    private final ComboMealRepository comboMealRepository;
    private final SideItemRepository sideItemRepository;
    private final DrinkRepository drinkRepository;

    private final MenuMapper menuMapper;
    private final PizzaMapper pizzaMapper;
    private final ComboMapper comboMapper;
    private final SideItemMapper sideItemMapper;
    private final DrinkMapper drinkMapper;

    @Override
    @Transactional
    @CacheEvict(value = {"menus", "activeMenus"}, allEntries = true)
    public MenuSummaryResponse createMenu(CreateMenuRequest request) {
        validateMenuDoesNotExist(request.getRegionCode(), request.getName());

        Menu menu = menuMapper.toEntity(request);
        menu.setIsActive(false);
        menu.setMetadata(new HashMap<>());

        Menu savedMenu = menuRepository.save(menu);
        log.info("Created new menu with ID: {}", savedMenu.getMenuId());
        return menuMapper.toSummaryResponse(savedMenu);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "menus", key = "#id")
    public MenuDetailedResponse getMenuById(String id) {
        log.debug("Fetching menu with ID: {}", id);
        Menu menu = findMenuById(id);
        return buildDetailedMenuResponse(menu);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("menus")
    public List<MenuSummaryResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(menuMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "activeMenus", key = "#regionCode")
    public List<MenuSummaryResponse> getActiveMenusByRegion(String regionCode) {
        return menuRepository.findActiveMenusByRegion(regionCode).stream()
                .map(menuMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuSummaryResponse> getMenusByDateRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        // Find menus where: validFrom <= endDate AND validTo >= startDate
        return menuRepository.findByValidFromLessThanEqualAndValidToGreaterThanEqual(endDate, startDate).stream()
                .map(menuMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"menus", "activeMenus"}, key = "#id")
    public MenuDetailedResponse updateMenu(String id, UpdateMenuRequest request) {
        Menu existingMenu = findMenuById(id);
        menuMapper.updateEntity(request, existingMenu);

        Menu updatedMenu = menuRepository.save(existingMenu);
        log.info("Updated menu with ID: {}", id);
        return menuMapper.toDetailedResponse(updatedMenu);
    }

    @Override
    @Transactional
    @CacheEvict(value = "activeMenus", allEntries = true)
    public void activateMenu(String id) {
        Menu menu = findMenuById(id);
        menu.setIsActive(true);
        menuRepository.save(menu);
        log.info("Activated menu with ID: {}", id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "activeMenus", allEntries = true)
    public void deactivateMenu(String id) {
        Menu menu = findMenuById(id);
        menu.setIsActive(false);
        menuRepository.save(menu);
        log.info("Deactivated menu with ID: {}", id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"menus", "activeMenus"}, key = "#id")
    public void deleteMenu(String id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu not found with id: " + id);
        }

        menuRepository.deleteById(id);
        log.info("Deleted menu with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private MenuDetailedResponse buildDetailedMenuResponse(Menu menu) {
        MenuDetailedResponse response = menuMapper.toDetailedResponse(menu);
        response.setPizzas(getPizzasForMenu(menu.getMenuId()));
        response.setCombos(getCombosForMenu(menu.getMenuId()));
        response.setSideItems(getSideItemsForMenu(menu.getMenuId()));
        response.setDrinks(getDrinksForMenu(menu.getMenuId()));
        return response;
    }

    private List<PizzaSummaryResponse> getPizzasForMenu(String menuId) {
        return pizzaRepository.findByMenuId(menuId).stream()
                .map(pizzaMapper::toSummaryResponse)
                .toList();
    }

    private List<ComboSummaryResponse> getCombosForMenu(String menuId) {
        return comboMealRepository.findByMenuId(menuId).stream()
                .map(comboMapper::toSummaryResponse)
                .toList();
    }

    private List<SideItemResponse> getSideItemsForMenu(String menuId) {
        return sideItemRepository.findByMenuId(menuId).stream()
                .map(sideItemMapper::toResponse)
                .toList();
    }

    private List<DrinkResponse> getDrinksForMenu(String menuId) {
        return drinkRepository.findByMenuId(menuId).stream()
                .map(drinkMapper::toResponse)
                .toList();
    }

    private Menu findMenuById(String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
    }

    private void validateMenuDoesNotExist(String regionCode, String name) {
        if (menuRepository.existsByRegionCodeAndName(regionCode, name)) {
            throw new DuplicateResourceException("Menu with this name already exists in the region");
        }
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date must be before end date");
        }
    }
}