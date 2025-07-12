package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.pizza.Topping;
import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import app.milanpizza.menucatalog.dto.response.pizza.ToppingResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ToppingMapper {
    @IgnoreAuditFields
    Topping toEntity(ToppingRequest request);

    ToppingResponse toToppingResponse(Topping topping);

    AllowedToppingResponse toAllowedToppingResponse(Topping topping);

    void updateEntity(ToppingRequest request, @MappingTarget Topping topping);
}