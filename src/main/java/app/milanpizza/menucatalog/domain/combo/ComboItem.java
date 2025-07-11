package app.milanpizza.menucatalog.domain.combo;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import app.milanpizza.menucatalog.domain.enums.ItemType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "combo_items")
public class ComboItem extends BaseDocument {
    @Id
    private String id; // Composite key handled separately

    @NotBlank
    private String comboId;

    @NotBlank
    private String itemId;

    @NotNull
    private ItemType itemType;

    @Min(1)
    private Integer quantity;

    @NotNull
    private Boolean isRequired;

    @NotNull
    private Boolean allowsSubstitution;

    private Map<String, Object> substitutionOptions;
}