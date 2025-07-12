package app.milanpizza.menucatalog.dto.request.config;

import app.milanpizza.menucatalog.domain.enums.PizzaBaseTexture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.Map;

@Data
public class PizzaBaseRequest {
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

    @Min(60) @Max(1200)
    private Integer defaultBakingTimeSeconds;

    @NotNull
    @Schema(description = "Texture of the pizza base", example = "THIN",
             allowableValues = {"THIN", "THICK", "STUFFED"})
    private PizzaBaseTexture texture;

    @DecimalMin("0.1")
    private Double thicknessMm;

    private Map<String, Object> ingredients;
}