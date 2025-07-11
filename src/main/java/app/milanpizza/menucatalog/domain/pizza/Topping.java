package app.milanpizza.menucatalog.domain.pizza;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "toppings")
public class Topping extends BaseDocument {
    @Id
    private String toppingId;

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