package app.milanpizza.menucatalog.dto.request.menu;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Map;

@Data
public class UpdateMenuRequest {
    @NotBlank
    private String name;

    private LocalDate validFrom;

    private LocalDate validTo;

    private Boolean isActive;

    private Map<String, Object> metadata;
}