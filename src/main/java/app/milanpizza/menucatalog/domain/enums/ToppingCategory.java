package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categories of pizza toppings", enumAsRef = true)
public enum ToppingCategory {
    CHEESE, MEAT, VEGETABLE, SAUCE, SPICE
}