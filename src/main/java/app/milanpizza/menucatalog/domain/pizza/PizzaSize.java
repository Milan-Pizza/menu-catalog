package app.milanpizza.menucatalog.domain.pizza;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.SizeName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "pizza_sizes")
public class PizzaSize extends BaseDocument {
    @Id
    private String sizeId;

    @NotNull
    private SizeName name;

    @DecimalMin("4.0")
    private Double diameterInches;

    @DecimalMin("0.5")
    private Double basePriceMultiplier;

    @DecimalMin("0.5")
    private Double toppingPriceMultiplier;

    @Min(1)
    private Integer servesPeople;

    @DecimalMin("50.0")
    private Double weightGrams;

    @NotNull
    private Boolean isPopular;
}