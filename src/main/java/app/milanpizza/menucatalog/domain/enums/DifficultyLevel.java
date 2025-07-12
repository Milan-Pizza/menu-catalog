package app.milanpizza.menucatalog.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Difficulty levels for pizza preparation", enumAsRef = true)
public enum DifficultyLevel {
    EASY, MEDIUM, HARD
}