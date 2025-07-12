package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available drink sizes", enumAsRef = true)
public enum DrinkSize {
    SMALL, MEDIUM, LARGE
}