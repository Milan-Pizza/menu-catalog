package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available pizza base textures", enumAsRef = true)
public enum PizzaBaseTexture {
    THIN, THICK, STUFFED
}