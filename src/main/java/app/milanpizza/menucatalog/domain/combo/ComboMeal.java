package app.milanpizza.menucatalog.domain.combo;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "combo_meals")
public class ComboMeal extends BaseDocument {
    @Id
    private String comboId;

    @NotBlank
    private String menuId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @DecimalMin("0.0")
    private Double originalPrice;

    @DecimalMin("0.0")
    private Double comboPrice;

    @DecimalMin("0.0")
    private Double discountAmount;

    @NotNull
    private Boolean isActive;

    @NotNull
    private LocalDateTime validFrom;

    @NotNull
    private LocalDateTime validTo;

    private String imageUrl;

    private Map<String, Object> termsConditions;
}