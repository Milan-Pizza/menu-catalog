package app.milanpizza.menucatalog.dto.response.combo;

import app.milanpizza.menucatalog.dto.response.metadata.NutritionalInfoResponse;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ComboDetailedResponse {
    private String comboId;
    private String menuId;
    private String name;
    private String description;
    private Double originalPrice;
    private Double comboPrice;
    private Double discountAmount;
    private Boolean isActive;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String imageUrl;
    private Map<String, Object> termsConditions;

    private List<ComboItemResponse> items;
    private NutritionalInfoResponse nutritionalInfo;
}