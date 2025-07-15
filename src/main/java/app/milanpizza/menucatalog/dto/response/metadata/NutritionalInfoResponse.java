package app.milanpizza.menucatalog.dto.response.metadata;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class NutritionalInfoResponse {
    private String nutritionId;
    private String itemId;
    private String itemType;
    private Double caloriesPerServing;
    private Double proteinG;
    private Double carbsG;
    private Double fatG;
    private Double fiberG;
    private Double sodiumMg;
    private Map<String, Object> allergenInfo;
    private Map<String, Object> vitaminContent;
    private LocalDateTime lastUpdated;
}