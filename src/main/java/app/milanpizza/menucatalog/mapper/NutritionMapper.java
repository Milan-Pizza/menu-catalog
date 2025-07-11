package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.metadata.NutritionalInfo;
import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NutritionMapper {
    NutritionalInfoResponse toResponse(NutritionalInfo nutritionalInfo);
}