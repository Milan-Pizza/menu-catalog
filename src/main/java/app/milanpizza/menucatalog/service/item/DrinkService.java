package app.milanpizza.menucatalog.service.item;

import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import java.util.List;

public interface DrinkService {
    DrinkResponse createDrink(CreateDrinkRequest request);
    DrinkResponse getDrinkById(String id);
    List<DrinkResponse> getAllDrinksByMenu(String menuId);
    List<DrinkResponse> getAvailableDrinksByMenu(String menuId);
    DrinkResponse updateDrink(String id, UpdateDrinkRequest request);
    void toggleDrinkAvailability(String id);
    void deleteDrink(String id);
}