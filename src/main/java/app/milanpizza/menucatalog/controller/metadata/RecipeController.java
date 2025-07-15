package app.milanpizza.menucatalog.controller.metadata;

import app.milanpizza.menucatalog.domain.enums.DifficultyLevel;
import app.milanpizza.menucatalog.dto.request.metadata.RecipeRequest;
import app.milanpizza.menucatalog.dto.response.metadata.RecipeResponse;
import app.milanpizza.menucatalog.service.metadata.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipes Management", description = "Manage pizza recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Create recipe",
            description = "Create a new recipe for a pizza")
    @ApiResponse(responseCode = "201", description = "Recipe created successfully",
            content = @Content(schema = @Schema(implementation = RecipeResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Pizza not found")
    @ApiResponse(responseCode = "409", description = "Recipe already exists for this pizza")
    public ResponseEntity<RecipeResponse> createRecipe(
            @Valid @RequestBody RecipeRequest request) {
        RecipeResponse response = recipeService.createRecipe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pizza/{pizzaId}")
    @Operation(summary = "Get recipe by pizza ID",
            description = "Retrieve recipe for a specific pizza")
    @ApiResponse(responseCode = "200", description = "Recipe found",
            content = @Content(schema = @Schema(implementation = RecipeResponse.class)))
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    public ResponseEntity<RecipeResponse> getRecipeByPizzaId(
            @Parameter(description = "ID of the pizza", required = true)
            @PathVariable String pizzaId) {
        RecipeResponse response = recipeService.getRecipeByPizzaId(pizzaId);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/difficulty/{level}")
//    @Operation(summary = "Get recipes by difficulty",
//            description = "Retrieve all recipes with a specific difficulty level")
//    @ApiResponse(responseCode = "200", description = "Recipes found",
//            content = @Content(schema = @Schema(implementation = RecipeResponse.class)))
//    public ResponseEntity<List<RecipeResponse>> getRecipesByDifficulty(
//            @Parameter(description = "Difficulty level (EASY, MEDIUM, HARD)", required = true)
//            @PathVariable DifficultyLevel level) {
//        List<RecipeResponse> responses = recipeService.getRecipesByDifficulty(level);
//        return ResponseEntity.ok(responses);
//    }
//
//    @GetMapping
//    @Operation(summary = "Get all pizza recipes",
//            description = "Retrieve all pizza recipes mapped by pizza ID")
//    @ApiResponse(responseCode = "200", description = "Recipes found",
//            content = @Content(schema = @Schema(implementation = Map.class)))
//    public ResponseEntity<Map<String, RecipeResponse>> getAllPizzaRecipes() {
//        Map<String, RecipeResponse> responses = recipeService.getAllPizzaRecipes();
//        return ResponseEntity.ok(responses);
//    }

    @PutMapping("/pizza/{pizzaId}")
    @Operation(summary = "Update recipe",
            description = "Update an existing recipe for a pizza")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully",
            content = @Content(schema = @Schema(implementation = RecipeResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    public ResponseEntity<RecipeResponse> updateRecipe(
            @Parameter(description = "ID of the pizza", required = true)
            @PathVariable String pizzaId,
            @Valid @RequestBody RecipeRequest request) {
        RecipeResponse response = recipeService.updateRecipe(pizzaId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pizza/{pizzaId}")
    @Operation(summary = "Delete recipe",
            description = "Delete recipe for a specific pizza")
    @ApiResponse(responseCode = "204", description = "Recipe deleted successfully")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    public ResponseEntity<Void> deleteRecipe(
            @Parameter(description = "ID of the pizza", required = true)
            @PathVariable String pizzaId) {
        recipeService.deleteRecipe(pizzaId);
        return ResponseEntity.noContent().build();
    }
}