package app.milanpizza.menucatalog.dto.response.menu;

import lombok.Data;
import java.time.LocalDate;
import java.util.Map;

@Data
public class MenuSummaryResponse {
    private String menuId;
    private String regionCode;
    private String name;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Boolean isActive;
    private Map<String, Object> metadata;

}