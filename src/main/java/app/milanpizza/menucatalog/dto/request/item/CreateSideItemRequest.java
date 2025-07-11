package app.milanpizza.menucatalog.dto.request.item;

import app.milanpizza.menucatalog.domain.enums.SideCategory;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CreateSideItemRequest {
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
    private SideCategory category;

    @Min(1) @Max(60)
    private Integer preparationTimeMinutes;

    @NotNull
    private Boolean isVegetarian;

    @NotNull
    private Boolean isVegan;

    private String imageUrl;
}