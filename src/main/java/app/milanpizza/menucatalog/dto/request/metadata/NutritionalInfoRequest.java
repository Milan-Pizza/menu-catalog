package app.milanpizza.menucatalog.dto.request.metadata;

import app.milanpizza.menucatalog.domain.enums.ItemType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class NutritionalInfoRequest {
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
}
