package app.milanpizza.menucatalog.service.metadata.impl;

import app.milanpizza.menucatalog.domain.metadata.Recipe;
import app.milanpizza.menucatalog.dto.response.shared.RecipeResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.RecipeMapper;
import app.milanpizza.menucatalog.repository.metadata.RecipeRepository;
import app.milanpizza.menucatalog.service.metadata.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    @Transactional(readOnly = true)
    public RecipeResponse getRecipeByPizzaId(String pizzaId) {
        Recipe recipe = recipeRepository.findByPizzaId(pizzaId);
        if (recipe == null) {
            throw new ResourceNotFoundException("Recipe not found for pizza id: " + pizzaId);
        }
        return recipeMapper.toResponse(recipe);
    }
}