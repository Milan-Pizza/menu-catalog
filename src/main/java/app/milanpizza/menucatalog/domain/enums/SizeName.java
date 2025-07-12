package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available pizza sizes", enumAsRef = true)
public enum SizeName {
    PERSONAL, SMALL, MEDIUM, LARGE, XLARGE
}