package app.milanpizza.menucatalog.service.pizza;

import app.milanpizza.menucatalog.dto.request.pizza.CreatePizzaRequest;
import app.milanpizza.menucatalog.dto.request.pizza.PizzaToppingConfigRequest;
import app.milanpizza.menucatalog.dto.request.pizza.UpdatePizzaRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaDetailedResponse;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSummaryResponse;
import java.util.List;

public interface PizzaService {
    PizzaDetailedResponse createPizza(CreatePizzaRequest request);
    PizzaDetailedResponse getPizzaById(String id);
    List<PizzaSummaryResponse> getAllPizzasByMenu(String menuId);
    List<PizzaSummaryResponse> getAvailablePizzasByMenu(String menuId);
    PizzaDetailedResponse updatePizza(String id, UpdatePizzaRequest request);
    void togglePizzaAvailability(String id);
    void addToppingToPizza(PizzaToppingConfigRequest request);
    void removeToppingFromPizza(String pizzaId, String toppingId);
    void deletePizza(String id);
}