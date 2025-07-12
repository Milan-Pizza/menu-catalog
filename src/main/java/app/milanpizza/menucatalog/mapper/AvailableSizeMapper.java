package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.pizza.AvailableSize;
import app.milanpizza.menucatalog.dto.request.pizza.PizzaSizeConfigRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AvailableSizeResponse;
import app.milanpizza.menucatalog.mapper.config.BaseMappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMappingConfig.class)
public interface AvailableSizeMapper {

     AvailableSize toEntity(PizzaSizeConfigRequest request);

     AvailableSizeResponse toAvailableSizeResponse(AvailableSize availableSize);

}
