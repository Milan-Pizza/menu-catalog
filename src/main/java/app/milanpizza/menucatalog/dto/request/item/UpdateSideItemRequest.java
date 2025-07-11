package app.milanpizza.menucatalog.dto.request.item;

import app.milanpizza.menucatalog.domain.enums.SideCategory;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UpdateSideItemRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @DecimalMin("0.0")
    private Double price;

    private Boolean isAvailable;

    private SideCategory category;

    @Min(1) @Max(60)
    private Integer preparationTimeMinutes;

    private Boolean isVegetarian;

    private Boolean isVegan;

    private String imageUrl;
}