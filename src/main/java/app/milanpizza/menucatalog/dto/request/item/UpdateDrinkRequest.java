package app.milanpizza.menucatalog.dto.request.item;

import app.milanpizza.menucatalog.domain.enums.DrinkCategory;
import app.milanpizza.menucatalog.domain.enums.DrinkSize;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UpdateDrinkRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @DecimalMin("0.0")
    private Double price;

    private Boolean isAvailable;

    private DrinkCategory category;

    private DrinkSize size;

    @DecimalMin("100.0")
    private Double volumeMl;

    private Boolean isCold;

    private String imageUrl;
}