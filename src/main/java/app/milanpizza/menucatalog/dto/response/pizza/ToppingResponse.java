package app.milanpizza.menucatalog.dto.response.pizza;

import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import lombok.Data;

@Data
public class ToppingResponse {
    private String toppingId;

    private String name;

    private ToppingCategory category;

    private Double baseCostPerUnit;

    private Boolean isPremium;

    private Boolean isVegetarian;

    private Boolean isVegan;

    private Integer preparationTimeSeconds;

    private String storageRequirements;
}
