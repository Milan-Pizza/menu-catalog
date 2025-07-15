package app.milanpizza.menucatalog.dto.response.combo;

import app.milanpizza.menucatalog.domain.enums.ItemType;
import lombok.Data;

import java.util.Map;

@Data
public class ComboItemResponse {
//    private String comboId;
    private String itemId;
    private ItemType itemType;
    private String itemName;
    private Integer quantity;
    private Boolean isRequired;
    private Boolean allowsSubstitution;
    private Map<String, Object> substitutionOptions;
}