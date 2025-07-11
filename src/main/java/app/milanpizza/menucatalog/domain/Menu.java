package app.milanpizza.menucatalog.domain;

import app.milanpizza.menucatalog.domain.base.BaseDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "menus")
@CompoundIndex(def = "{'regionCode': 1, 'name': 1}", unique = true)
public class Menu extends BaseDocument {
    @Id
    private String menuId;

    @NotBlank
    private String regionCode;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;

    @NotNull
    private Boolean isActive;

    private Map<String, Object> metadata;
}