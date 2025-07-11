package app.milanpizza.menucatalog.dto.response.pizza;

import app.milanpizza.menucatalog.domain.enums.PizzaCategory;
import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;
import app.milanpizza.menucatalog.dto.response.shared.RecipeResponse;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class PizzaDetailedResponse {
    private String pizzaId;
    private String menuId;
    private String name;
    private String description;
    private Boolean isAvailable;
    private String imageUrl;
    private Integer basePreparationTimeMinutes;
    private Boolean allowCustomization;
    private Double basePrice;
    private PizzaCategory category;
    private Integer spiceLevel;
    private Boolean isPopular;
    private Map<String, Object> tags;

    private PizzaBaseResponse base;
    private List<AvailableSizeResponse> availableSizes;
    private List<AllowedToppingResponse> allowedToppings;
    private RecipeResponse recipe;
    private NutritionalInfoResponse nutritionalInfo;
}