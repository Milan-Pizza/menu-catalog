package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.Menu;
import app.milanpizza.menucatalog.dto.request.menu.CreateMenuRequest;
import app.milanpizza.menucatalog.dto.request.menu.UpdateMenuRequest;
import app.milanpizza.menucatalog.dto.response.menu.MenuDetailedResponse;
import app.milanpizza.menucatalog.dto.response.menu.MenuSummaryResponse;
import app.milanpizza.menucatalog.mapper.config.BaseMappingConfig;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMappingConfig.class,
        uses = {PizzaMapper.class, ComboMapper.class, SideItemMapper.class, DrinkMapper.class})
public interface MenuMapper {

    @IgnoreAuditFields
    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    Menu toEntity(CreateMenuRequest request);

    @IgnoreAuditFields
    @Mapping(target = "regionCode", ignore = true)
    void updateEntity(UpdateMenuRequest request, @MappingTarget Menu menu);

    MenuSummaryResponse toSummaryResponse(Menu menu);

//    @Mapping(source = "pizzas", target = "pizzas")
    MenuDetailedResponse toDetailedResponse(Menu menu);
}