package app.milanpizza.menucatalog.domain.item;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.DrinkCategory;
import app.milanpizza.menucatalog.domain.enums.DrinkSize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "drinks")
public class Drink extends BaseDocument {
    @Id
    private String drinkId;

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