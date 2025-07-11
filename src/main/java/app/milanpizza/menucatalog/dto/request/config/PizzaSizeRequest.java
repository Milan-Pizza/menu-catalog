package app.milanpizza.menucatalog.dto.request.config;

import app.milanpizza.menucatalog.domain.enums.SizeName;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class PizzaSizeRequest {
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