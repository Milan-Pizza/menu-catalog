package app.milanpizza.menucatalog.dto.response.pizza;

import app.milanpizza.menucatalog.domain.enums.PizzaBaseTexture;
import lombok.Data;
import java.util.Map;

@Data
public class PizzaBaseResponse {
    private String baseId;
    private String name;
    private String description;
    private Double basePrice;
    private Boolean isGlutenFree;
    private Boolean isVegan;
    private Integer defaultBakingTimeSeconds;
    private PizzaBaseTexture texture;
    private Double thicknessMm;
    private Map<String, Object> ingredients;
}