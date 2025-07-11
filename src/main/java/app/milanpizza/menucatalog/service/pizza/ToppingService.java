package app.milanpizza.menucatalog.service.pizza;

import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import java.util.List;

public interface ToppingService {
    AllowedToppingResponse createTopping(ToppingRequest request);
    AllowedToppingResponse getToppingById(String id);
    List<AllowedToppingResponse> getAllToppings();
    List<AllowedToppingResponse> getToppingsByCategory(String category);
    AllowedToppingResponse updateTopping(String id, ToppingRequest request);
    void deleteTopping(String id);
}