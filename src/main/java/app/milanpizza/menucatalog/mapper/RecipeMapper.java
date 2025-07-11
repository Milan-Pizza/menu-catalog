package app.milanpizza.menucatalog.mapper;

import app.milanpizza.menucatalog.domain.metadata.Recipe;
import app.milanpizza.menucatalog.dto.response.shared.RecipeResponse;
import app.milanpizza.menucatalog.mapper.config.IgnoreAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeResponse toResponse(Recipe recipe);
}