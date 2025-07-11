package app.milanpizza.menucatalog.dto.response.pizza;

import lombok.Data;

@Data
public class AvailableSizeResponse {
    private String pizzaId;
    private String sizeId;
    private String sizeName;
    private Boolean isAvailable;
    private Double sizeSurcharge;
    private Boolean isPopular;
}