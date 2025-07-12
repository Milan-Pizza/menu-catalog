package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.pizza.Pizza;
import app.milanpizza.menucatalog.dto.request.pizza.CreatePizzaRequest;
import app.milanpizza.menucatalog.dto.request.pizza.UpdatePizzaRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaDetailedResponse;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSummaryResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PizzaMapper {
    @IgnoreAuditFields
    Pizza toEntity(CreatePizzaRequest request);

    PizzaSummaryResponse toSummaryResponse(Pizza pizza);

    PizzaDetailedResponse toDetailedResponse(Pizza pizza);

    @IgnoreAuditFields
    void updateEntity(UpdatePizzaRequest request, @MappingTarget Pizza pizza);
}