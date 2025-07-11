package app.milanpizza.menucatalog.dto.request.pizza;

import app.milanpizza.menucatalog.domain.enums.PizzaCategory;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.Map;

@Data
public class CreatePizzaRequest {
    @NotBlank
    private String menuId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private Boolean isAvailable;

    private String imageUrl;

    @Min(5) @Max(60)
    private Integer basePreparationTimeMinutes;

    @NotNull
    private Boolean allowCustomization;

    @DecimalMin("0.0")
    private Double basePrice;

    @NotNull
    private PizzaCategory category;

    @Min(0) @Max(5)
    private Integer spiceLevel;

    @NotNull
    private Boolean isPopular;

    private Map<String, Object> tags;
}