package app.milanpizza.menucatalog.dto.request.pizza;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class PizzaToppingConfigRequest {
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