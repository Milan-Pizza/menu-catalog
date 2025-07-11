package app.milanpizza.menucatalog.domain.pizza;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.PizzaCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "pizzas")
public class Pizza extends BaseDocument {
    @Id
    private String pizzaId;

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

    @Min(5)
    @Max(60)
    private Integer basePreparationTimeMinutes;

    @NotNull
    private Boolean allowCustomization;

    @DecimalMin("0.0")
    private Double basePrice;

    @NotNull
    private PizzaCategory category;

    @Min(0)
    @Max(5)
    private Integer spiceLevel;

    @NotNull
    private Boolean isPopular;

    private Map<String, Object> tags;
}