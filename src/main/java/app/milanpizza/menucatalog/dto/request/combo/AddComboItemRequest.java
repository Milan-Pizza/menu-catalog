package app.milanpizza.menucatalog.dto.request.combo;

import app.milanpizza.menucatalog.domain.enums.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class AddComboItemRequest {
    @NotBlank
    private String itemId;

    @NotNull
    private ItemType itemType; // PIZZA, SIDE, DRINK

    @Min(1)
    private Integer quantity = 1;

    @NotNull
    private Boolean isRequired = true;

    @NotNull
    private Boolean allowsSubstitution = false;

    private Map<String, Object> substitutionOptions;
}