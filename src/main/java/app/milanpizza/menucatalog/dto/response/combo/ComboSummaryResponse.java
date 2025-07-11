package app.milanpizza.menucatalog.dto.response.combo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ComboSummaryResponse {
    private String comboId;
    private String name;
    private Double comboPrice;
    private Double discountAmount;
    private Boolean isActive;
}