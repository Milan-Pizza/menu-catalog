package app.milanpizza.menucatalog.service.pizza;

import app.milanpizza.menucatalog.dto.request.config.PizzaSizeRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSizeResponse;

import java.util.List;

public interface PizzaSizeService {

     PizzaSizeResponse createPizzaSize(PizzaSizeRequest request);
     PizzaSizeResponse getPizzaSizeById(String id);
     List<PizzaSizeResponse> getAllPizzaSizes();
     PizzaSizeResponse updatePizzaSize(String id, PizzaSizeRequest request);
     void deletePizzaSize(String id);
}
