package app.milanpizza.menucatalog.controller.metadata;

import app.milanpizza.menucatalog.dto.response.shared.RecipeResponse;
import app.milanpizza.menucatalog.service.metadata.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/pizza/{pizzaId}")
    public ResponseEntity<RecipeResponse> getRecipeByPizzaId(@PathVariable String pizzaId) {
        RecipeResponse response = recipeService.getRecipeByPizzaId(pizzaId);
        return ResponseEntity.ok(response);
    }
}