package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available side categories for menu items", enumAsRef = true)
public enum SideCategory {
    APPETIZER, SALAD, BREAD, DESSERT
}