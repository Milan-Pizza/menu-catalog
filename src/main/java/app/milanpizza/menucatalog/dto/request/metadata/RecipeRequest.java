package app.milanpizza.menucatalog.dto.request.metadata;

import app.milanpizza.menucatalog.domain.enums.DifficultyLevel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class RecipeRequest {
    @NotBlank
    private String pizzaId;

    private Map<String, Object> preparationSteps;

    private Map<String, Object> cookingInstructions;

    @Min(5)
    @Max(120)
    private Integer totalPrepTimeMinutes;

    @Min(5)
    @Max(60)
    private Integer cookingTimeMinutes;

    @NotNull
    private DifficultyLevel difficultyLevel;

    private Map<String, Object> equipmentRequired;

    private Map<String, Object> qualityStandards;
}