package app.milanpizza.menucatalog.dto.request.item;

import app.milanpizza.menucatalog.domain.enums.DrinkCategory;
import app.milanpizza.menucatalog.domain.enums.DrinkSize;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CreateDrinkRequest {
    @NotBlank
    private String menuId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @DecimalMin("0.0")
    private Double price;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private DrinkCategory category;

    @NotNull
    private DrinkSize size;

    @DecimalMin("100.0")
    private Double volumeMl;

    @NotNull
    private Boolean isCold;

    private String imageUrl;
}