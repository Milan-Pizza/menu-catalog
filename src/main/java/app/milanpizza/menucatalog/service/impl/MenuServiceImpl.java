package app.milanpizza.menucatalog.service.impl;

import app.milanpizza.menucatalog.domain.Menu;
import app.milanpizza.menucatalog.domain.pizza.Pizza;
import app.milanpizza.menucatalog.dto.request.menu.CreateMenuRequest;
import app.milanpizza.menucatalog.dto.request.menu.UpdateMenuRequest;
import app.milanpizza.menucatalog.dto.response.menu.MenuDetailedResponse;
import app.milanpizza.menucatalog.dto.response.menu.MenuSummaryResponse;
import app.milanpizza.menucatalog.exception.BadRequestException;
import app.milanpizza.menucatalog.mapper.MenuMapper;
import app.milanpizza.menucatalog.mapper.PizzaMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.pizza.PizzaRepository;
import app.milanpizza.menucatalog.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    @Override
    @Transactional
    public MenuSummaryResponse createMenu(CreateMenuRequest request) {
        if (menuRepository.existsByRegionCodeAndName(request.getRegionCode(), request.getName())) {
            throw new BadRequestException("Menu with this name already exists in the region");
        }

        Menu menu = menuMapper.toEntity(request);
        menu.setIsActive(false); // Default to inactive
        menu.setMetadata(new HashMap<>()); // Initialize metadata if needed
        Menu savedMenu = menuRepository.save(menu);
        return menuMapper.toSummaryResponse(savedMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDetailedResponse getMenuById(String id) {
        // Get menu by id
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));

        // Get the menu with pizzas populated
        MenuDetailedResponse response = menuMapper.toDetailedResponse(menu);

        // Fetch pizzas from pizza collection using menuId and populate the response
        List<Pizza> pizzas = pizzaRepository.findByMenuId(id);
        System.out.println(pizzas);
//        response.setPizzas(pizzas);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuSummaryResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(menuMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuSummaryResponse> getActiveMenusByRegion(String regionCode) {
        return menuRepository.findActiveMenusByRegion(regionCode).stream()
                .map(menuMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuSummaryResponse> getMenusByDateRange(LocalDate startDate, LocalDate endDate) {
        return menuRepository.findByValidFromBetween(startDate, endDate).stream()
                .map(menuMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    public MenuDetailedResponse updateMenu(String id, UpdateMenuRequest request) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));

        menuMapper.updateEntity(request, existingMenu);
        Menu updatedMenu = menuRepository.save(existingMenu);
        return menuMapper.toDetailedResponse(updatedMenu);
    }

    @Override
    @Transactional
    public void activateMenu(String id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
        menu.setIsActive(true);
        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void deactivateMenu(String id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
        menu.setIsActive(false);
        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void deleteMenu(String id) {
        if (!menuRepository.existsById(id)) {
            throw new NoSuchElementException("Menu not found with id: " + id);
        }
        menuRepository.deleteById(id);
    }
}