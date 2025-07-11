package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.pizza.PizzaBase;
import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PizzaBaseMapper {
    @IgnoreAuditFields
    PizzaBase toEntity(PizzaBaseRequest request);

    PizzaBaseResponse toResponse(PizzaBase pizzaBase);

    @IgnoreAuditFields
    void updateEntity(PizzaBaseRequest request, @MappingTarget PizzaBase pizzaBase);
}