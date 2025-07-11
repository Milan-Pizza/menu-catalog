package app.milanpizza.menucatalog.service.metadata;

import app.milanpizza.menucatalog.dto.response.shared.RecipeResponse;

public interface RecipeService {
    RecipeResponse getRecipeByPizzaId(String pizzaId);
}