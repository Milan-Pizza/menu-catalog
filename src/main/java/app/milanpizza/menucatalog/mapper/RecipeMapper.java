package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.metadata.Recipe;
import app.milanpizza.menucatalog.dto.request.metadata.RecipeRequest;
import app.milanpizza.menucatalog.dto.response.metadata.RecipeResponse;
import app.milanpizza.menucatalog.mapper.config.BaseMappingConfig;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(config = BaseMappingConfig.class)
public interface RecipeMapper {
    Recipe toEntity(RecipeRequest request);

    RecipeResponse toResponse(Recipe response);

//    @Mapping(target = "pizzaId", ignore = true)
//    @Mapping(target = "preparationSteps", source = "request.preparationSteps")
//    @Mapping(target = "preparationSteps", source = "request.preparationSteps")
//    @Mapping(target = "cookingInstructions", source = "request.cookingInstructions")
    @IgnoreAuditFields
    void updateEntity(RecipeRequest request, @MappingTarget Recipe recipe);
}