package app.milanpizza.menucatalog.dto.response.menu;

import app.milanpizza.menucatalog.dto.response.pizza.PizzaSummaryResponse;
import app.milanpizza.menucatalog.dto.response.combo.ComboSummaryResponse;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class MenuDetailedResponse {
    private String menuId;
    private String regionCode;
    private String name;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Boolean isActive;
    private Map<String, Object> metadata;

    private List<PizzaSummaryResponse> pizzas;
    private List<ComboSummaryResponse> combos;
    private List<SideItemResponse> sideItems;
    private List<DrinkResponse> drinks;
}