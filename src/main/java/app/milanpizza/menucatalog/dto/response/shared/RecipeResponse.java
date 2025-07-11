package app.milanpizza.menucatalog.dto.response.shared;

import app.milanpizza.menucatalog.domain.enums.DifficultyLevel;
import lombok.Data;
import java.util.Map;

@Data
public class RecipeResponse {
    private String recipeId;
    private String pizzaId;
    private Map<String, Object> preparationSteps;
    private Map<String, Object> cookingInstructions;
    private Integer totalPrepTimeMinutes;
    private Integer cookingTimeMinutes;
    private DifficultyLevel difficultyLevel;
    private Map<String, Object> equipmentRequired;
    private Map<String, Object> qualityStandards;
}