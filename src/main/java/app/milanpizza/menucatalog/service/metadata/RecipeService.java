package app.milanpizza.menucatalog.service.metadata;

import app.milanpizza.menucatalog.dto.request.metadata.RecipeRequest;
import app.milanpizza.menucatalog.dto.response.metadata.RecipeResponse;
import jakarta.validation.Valid;

public interface RecipeService {
    RecipeResponse createRecipe(@Valid RecipeRequest request);
    RecipeResponse getRecipeByPizzaId(String pizzaId);
    RecipeResponse updateRecipe(String pizzaId, @Valid RecipeRequest request);
    void deleteRecipe(String pizzaId);
}