package app.milanpizza.menucatalog.service.combo;

import app.milanpizza.menucatalog.dto.request.combo.AddComboItemRequest;
import app.milanpizza.menucatalog.dto.request.combo.CreateComboRequest;
import app.milanpizza.menucatalog.dto.request.combo.UpdateComboItemRequest;
import app.milanpizza.menucatalog.dto.request.combo.UpdateComboRequest;
import app.milanpizza.menucatalog.dto.response.combo.ComboDetailedResponse;
import app.milanpizza.menucatalog.dto.response.combo.ComboItemResponse;
import app.milanpizza.menucatalog.dto.response.combo.ComboSummaryResponse;
import java.util.List;

public interface ComboService {
    ComboDetailedResponse createCombo(CreateComboRequest request);
    ComboDetailedResponse getComboById(String id);
    List<ComboSummaryResponse> getAllCombosByMenu(String menuId);
    List<ComboSummaryResponse> getActiveCombosByMenu(String menuId);
    ComboDetailedResponse updateCombo(String id, UpdateComboRequest request);
    void toggleComboAvailability(String id);
    void deleteCombo(String id);

    void removeItemFromCombo(String comboId, String itemId);
    ComboItemResponse updateComboItem(String comboId, String itemId, UpdateComboItemRequest request);
    List<ComboItemResponse> getComboItems(String comboId);
    ComboItemResponse addItemToCombo(String comboId, AddComboItemRequest request);
}