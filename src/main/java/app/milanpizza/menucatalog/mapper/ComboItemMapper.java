package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.combo.ComboItem;
import app.milanpizza.menucatalog.dto.response.combo.ComboItemResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComboItemMapper {
    ComboItemResponse toResponse(ComboItem comboItem);
}