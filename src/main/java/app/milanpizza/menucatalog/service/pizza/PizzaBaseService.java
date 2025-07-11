package app.milanpizza.menucatalog.service.pizza;

import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import java.util.List;

public interface PizzaBaseService {
    PizzaBaseResponse createPizzaBase(PizzaBaseRequest request);
    PizzaBaseResponse getPizzaBaseById(String id);
    List<PizzaBaseResponse> getAllPizzaBases();
    List<PizzaBaseResponse> getPizzaBasesByTexture(String texture);
    PizzaBaseResponse updatePizzaBase(String id, PizzaBaseRequest request);
    void deletePizzaBase(String id);
}