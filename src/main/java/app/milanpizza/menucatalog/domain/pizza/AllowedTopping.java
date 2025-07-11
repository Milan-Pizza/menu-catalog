package app.milanpizza.menucatalog.domain.pizza;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "allowed_toppings")
public class AllowedTopping extends BaseDocument {
    @Id
    private String id; // Composite key handled separately

    @NotBlank
    private String pizzaId;

    @NotBlank
    private String toppingId;

    @DecimalMin("0.0")
    private Double baseAdditionalPrice;

    @NotNull
    private Boolean isDefault;

    @NotNull
    private Boolean isRecommended;

    @Min(0)
    private Integer maxQuantity;
}