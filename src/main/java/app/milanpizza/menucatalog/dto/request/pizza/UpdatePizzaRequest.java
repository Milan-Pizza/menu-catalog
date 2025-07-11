package app.milanpizza.menucatalog.dto.request.pizza;

import app.milanpizza.menucatalog.domain.enums.PizzaCategory;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.Map;

@Data
public class UpdatePizzaRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    private Boolean isAvailable;

    private String imageUrl;

    @Min(5) @Max(60)
    private Integer basePreparationTimeMinutes;

    private Boolean allowCustomization;

    @DecimalMin("0.0")
    private Double basePrice;

    private PizzaCategory category;

    @Min(0) @Max(5)
    private Integer spiceLevel;

    private Boolean isPopular;

    private Map<String, Object> tags;
}