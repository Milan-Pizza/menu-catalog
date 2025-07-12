package app.milanpizza.menucatalog.dto.request.pizza;

import lombok.Data;

@Data
public class PizzaSizeConfigRequest {
    private String pizzaId;

    private String sizeId;

    private Double sizeSurcharge;

    private Boolean isAvailable;

    private Boolean isPopular;
}
