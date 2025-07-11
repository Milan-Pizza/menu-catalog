package app.milanpizza.menucatalog.dto.response.item;

import app.milanpizza.menucatalog.domain.enums.SideCategory;
import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;
import lombok.Data;

@Data
public class SideItemResponse {
    private String sideId;
    private String menuId;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private SideCategory category;
    private Integer preparationTimeMinutes;
    private Boolean isVegetarian;
    private Boolean isVegan;
    private String imageUrl;
    private NutritionalInfoResponse nutritionalInfo;
}