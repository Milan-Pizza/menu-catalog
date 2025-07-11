package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.pizza.PizzaSize;
import app.milanpizza.menucatalog.dto.request.config.PizzaSizeRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSizeResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PizzaSizeMapper {
    @IgnoreAuditFields
    PizzaSize toEntity(PizzaSizeRequest request);

    PizzaSizeResponse toResponse(PizzaSize pizzaSize);

    @IgnoreAuditFields
    void updateEntity(PizzaSizeRequest request, @MappingTarget PizzaSize pizzaSize);
}