package app.milanpizza.menucatalog.dto.response.pizza;

import lombok.Data;

@Data
public class AllowedToppingResponse {
//    private String pizzaId;
    private String toppingId;
    private String toppingName;
    private Double baseAdditionalPrice;
    private Boolean isDefault;
    private Boolean isRecommended;
    private Integer maxQuantity;
}