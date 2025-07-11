package app.milanpizza.menucatalog.service.item;

import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import java.util.List;

public interface SideItemService {
    SideItemResponse createSideItem(CreateSideItemRequest request);
    SideItemResponse getSideItemById(String id);
    List<SideItemResponse> getAllSideItemsByMenu(String menuId);
    List<SideItemResponse> getAvailableSideItemsByMenu(String menuId);
    SideItemResponse updateSideItem(String id, UpdateSideItemRequest request);
    void toggleSideItemAvailability(String id);
    void deleteSideItem(String id);
}