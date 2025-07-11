package app.milanpizza.menucatalog.domain.pizza;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.PizzaBaseTexture;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "pizza_bases")
public class PizzaBase extends BaseDocument {
    @Id
    private String baseId;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @DecimalMin("0.0")
    private Double basePrice;

    @NotNull
    private Boolean isGlutenFree;

    @NotNull
    private Boolean isVegan;

    @Min(60)
    @Max(1200)
    private Integer defaultBakingTimeSeconds;

    @NotNull
    private PizzaBaseTexture texture;

    @DecimalMin("0.1")
    private Double thicknessMm;

    private Map<String, Object> ingredients;
}