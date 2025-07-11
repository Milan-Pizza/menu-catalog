package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.item.Drink;
import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DrinkMapper {
    @IgnoreAuditFields
    Drink toEntity(CreateDrinkRequest request);

    DrinkResponse toResponse(Drink drink);

    @IgnoreAuditFields
    void updateEntity(UpdateDrinkRequest request, @MappingTarget Drink drink);
}