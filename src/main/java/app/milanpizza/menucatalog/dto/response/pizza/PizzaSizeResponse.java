package app.milanpizza.menucatalog.dto.response.pizza;

import app.milanpizza.menucatalog.domain.enums.SizeName;
import lombok.Data;

@Data
public class PizzaSizeResponse {
    private String sizeId;
    private SizeName name;
    private Double diameterInches;
    private Double basePriceMultiplier;
    private Double toppingPriceMultiplier;
    private Integer servesPeople;
    private Double weightGrams;
    private Boolean isPopular;
}