package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available drink categories", enumAsRef = true)
public enum DrinkCategory {
    SOFT_DRINK, JUICE, WATER, HOT_BEVERAGE
}