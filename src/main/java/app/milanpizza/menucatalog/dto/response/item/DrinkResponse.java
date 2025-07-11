package app.milanpizza.menucatalog.dto.response.item;

import app.milanpizza.menucatalog.domain.enums.DrinkCategory;
import app.milanpizza.menucatalog.domain.enums.DrinkSize;
import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;
import lombok.Data;

@Data
public class DrinkResponse {
    private String drinkId;
    private String menuId;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private DrinkCategory category;
    private DrinkSize size;
    private Double volumeMl;
    private Boolean isCold;
    private String imageUrl;
    private NutritionalInfoResponse nutritionalInfo;
}