package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of menu items", enumAsRef = true)
public enum ItemType {
    PIZZA, SIDE, DRINK
}