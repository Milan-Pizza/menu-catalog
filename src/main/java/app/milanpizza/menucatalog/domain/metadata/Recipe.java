package app.milanpizza.menucatalog.domain.metadata;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.DifficultyLevel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "recipes")
public class Recipe extends BaseDocument {
    @Id
    private String recipeId;

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