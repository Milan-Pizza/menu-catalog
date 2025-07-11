package app.milanpizza.menucatalog.dto.response.pizza;

import app.milanpizza.menucatalog.domain.enums.PizzaCategory;
import lombok.Data;
import java.util.Map;

@Data
public class PizzaSummaryResponse {
    private String pizzaId;
    private String name;
    private String description;
    private Boolean isAvailable;
    private Double basePrice;
    private PizzaCategory category;
    private Boolean isPopular;
}