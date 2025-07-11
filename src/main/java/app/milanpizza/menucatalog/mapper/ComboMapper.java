package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.combo.ComboMeal;
import app.milanpizza.menucatalog.dto.request.combo.CreateComboRequest;
import app.milanpizza.menucatalog.dto.request.combo.UpdateComboRequest;
import app.milanpizza.menucatalog.dto.response.combo.ComboDetailedResponse;
import app.milanpizza.menucatalog.dto.response.combo.ComboSummaryResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ComboMapper {
    @IgnoreAuditFields
    ComboMeal toEntity(CreateComboRequest request);

    ComboSummaryResponse toSummaryResponse(ComboMeal combo);

    ComboDetailedResponse toDetailedResponse(ComboMeal combo);

    @IgnoreAuditFields
    void updateEntity(UpdateComboRequest request, @MappingTarget ComboMeal combo);
}