package app.milanpizza.menucatalog.service.pizza;

import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import app.milanpizza.menucatalog.dto.response.pizza.ToppingResponse;

import java.util.List;

public interface ToppingService {
    ToppingResponse createTopping(ToppingRequest request);
    ToppingResponse getToppingById(String id);
    List<ToppingResponse> getAllToppings();
    List<ToppingResponse> getToppingsByCategory(String category);
    ToppingResponse updateTopping(String id, ToppingRequest request);
    void deleteTopping(String id);
}