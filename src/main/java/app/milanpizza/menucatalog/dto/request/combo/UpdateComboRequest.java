package app.milanpizza.menucatalog.dto.request.combo;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UpdateComboRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @DecimalMin("0.0")
    private Double originalPrice;

    @DecimalMin("0.0")
    private Double comboPrice;

    @DecimalMin("0.0")
    private Double discountAmount;

    private Boolean isActive;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private String imageUrl;

    private Map<String, Object> termsConditions;
}