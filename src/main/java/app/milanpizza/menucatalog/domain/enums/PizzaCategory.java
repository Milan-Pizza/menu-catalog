package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available pizza categories", enumAsRef = true)
public enum PizzaCategory {
    CLASSIC, PREMIUM, SIGNATURE, VEGAN
}