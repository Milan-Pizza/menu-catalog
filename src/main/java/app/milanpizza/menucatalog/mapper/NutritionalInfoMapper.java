package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.metadata.NutritionalInfo;
import app.milanpizza.menucatalog.dto.request.metadata.NutritionalInfoRequest;
import app.milanpizza.menucatalog.dto.response.metadata.NutritionalInfoResponse;
import app.milanpizza.menucatalog.mapper.config.BaseMappingConfig;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMappingConfig.class)
public interface NutritionalInfoMapper {
    NutritionalInfo toEntity(NutritionalInfoRequest request);

    NutritionalInfoResponse toResponse(NutritionalInfo response);

    @IgnoreAuditFields
    void updateEntity(NutritionalInfoRequest request, @MappingTarget  NutritionalInfo existingInfo);
}