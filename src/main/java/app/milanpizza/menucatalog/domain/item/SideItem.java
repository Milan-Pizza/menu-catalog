package app.milanpizza.menucatalog.domain.item;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.SideCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "side_items")
public class SideItem extends BaseDocument {
    @Id
    private String sideId;

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

    @Min(1)
    @Max(60)
    private Integer preparationTimeMinutes;

    @NotNull
    private Boolean isVegetarian;

    @NotNull
    private Boolean isVegan;

    private String imageUrl;
}