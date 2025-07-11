package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.item.SideItem;
import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SideItemMapper {
    @IgnoreAuditFields
    SideItem toEntity(CreateSideItemRequest request);

    SideItemResponse toResponse(SideItem sideItem);

    @IgnoreAuditFields
    void updateEntity(UpdateSideItemRequest request, @MappingTarget SideItem sideItem);
}