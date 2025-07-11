package app.milanpizza.menucatalog.dto.request.menu;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Data
public class CreateMenuRequest {
    @NotBlank
    private String regionCode;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;

//    @NotNull
    private Boolean isActive;

    private Map<String, Object> metadata;
}