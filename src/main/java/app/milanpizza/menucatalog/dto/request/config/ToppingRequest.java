package app.milanpizza.menucatalog.dto.request.config;

import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ToppingRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    private ToppingCategory category;

    @DecimalMin("0.0")
    private Double baseCostPerUnit;

    @NotNull
    private Boolean isPremium;

    @NotNull
    private Boolean isVegetarian;

    @NotNull
    private Boolean isVegan;

    @Min(0)
    private Integer preparationTimeSeconds;

    private String storageRequirements;
}