package app.milanpizza.menucatalog.domain.pizza;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "available_sizes")
public class AvailableSize extends BaseDocument {
    @Id
    private String id; // Composite key handled separately

    @NotBlank
    private String pizzaId;

    @NotBlank
    private String sizeId;

    @NotNull
    private Boolean isAvailable;

    @DecimalMin("0.0")
    private Double sizeSurcharge;

    @NotNull
    private Boolean isPopular;
}