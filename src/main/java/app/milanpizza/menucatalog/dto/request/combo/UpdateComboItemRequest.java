package app.milanpizza.menucatalog.dto.request.combo;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Map;

@Data
public class UpdateComboItemRequest {
    @Min(1)
    private Integer quantity;

    private Boolean isRequired;
    private Boolean allowsSubstitution;
    private Map<String, Object> substitutionOptions;
}