package app.milanpizza.menucatalog.domain.metadata;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.ItemType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "nutritional_info")
public class NutritionalInfo extends BaseDocument {
    @Id
    private String nutritionId;

    @NotBlank
    private String itemId;

    @NotNull
    private ItemType itemType;

    @DecimalMin("0.0")
    private Double caloriesPerServing;

    @DecimalMin("0.0")
    private Double proteinG;

    @DecimalMin("0.0")
    private Double carbsG;

    @DecimalMin("0.0")
    private Double fatG;

    @DecimalMin("0.0")
    private Double fiberG;

    @DecimalMin("0.0")
    private Double sodiumMg;

    private Map<String, Object> allergenInfo;

    private Map<String, Object> vitaminContent;

    @NotNull
    private LocalDateTime lastUpdated;
}