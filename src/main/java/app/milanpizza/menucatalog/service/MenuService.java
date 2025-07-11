package app.milanpizza.menucatalog.service;

import app.milanpizza.menucatalog.dto.request.menu.CreateMenuRequest;
import app.milanpizza.menucatalog.dto.request.menu.UpdateMenuRequest;
import app.milanpizza.menucatalog.dto.response.menu.MenuDetailedResponse;
import app.milanpizza.menucatalog.dto.response.menu.MenuSummaryResponse;
import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    MenuSummaryResponse createMenu(CreateMenuRequest request);
    MenuDetailedResponse getMenuById(String id);
    List<MenuSummaryResponse> getAllMenus();
    List<MenuSummaryResponse> getActiveMenusByRegion(String regionCode);
    List<MenuSummaryResponse> getMenusByDateRange(LocalDate startDate, LocalDate endDate);
    MenuDetailedResponse updateMenu(String id, UpdateMenuRequest request);
    void activateMenu(String id);
    void deactivateMenu(String id);
    void deleteMenu(String id);
}