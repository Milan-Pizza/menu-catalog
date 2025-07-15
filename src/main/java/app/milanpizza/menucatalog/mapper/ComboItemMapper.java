package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.combo.ComboItem;
import app.milanpizza.menucatalog.dto.request.combo.AddComboItemRequest;
import app.milanpizza.menucatalog.dto.request.combo.UpdateComboItemRequest;
import app.milanpizza.menucatalog.dto.response.combo.ComboItemResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComboItemMapper {
    ComboItemResponse toResponse(ComboItem comboItem);

    ComboItem toEntity(AddComboItemRequest request);

    ComboItem updateEntity(UpdateComboItemRequest request, @MappingTarget ComboItem item);
}