package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.pizza.AllowedTopping;
import app.milanpizza.menucatalog.dto.request.pizza.PizzaToppingConfigRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import app.milanpizza.menucatalog.mapper.config.BaseMappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMappingConfig.class)
public interface AllowedToppingMapper {

    AllowedTopping toEntity(PizzaToppingConfigRequest request);

    AllowedToppingResponse toAllowedToppingResponse(AllowedTopping allowedTopping);
}
