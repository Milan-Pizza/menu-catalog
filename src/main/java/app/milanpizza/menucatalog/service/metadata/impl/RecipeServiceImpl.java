package app.milanpizza.menucatalog.service.metadata.impl;

import app.milanpizza.menucatalog.domain.metadata.Recipe;
import app.milanpizza.menucatalog.dto.request.metadata.RecipeRequest;
import app.milanpizza.menucatalog.dto.response.metadata.RecipeResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.RecipeMapper;
import app.milanpizza.menucatalog.repository.metadata.RecipeRepository;
import app.milanpizza.menucatalog.repository.pizza.PizzaRepository;
import app.milanpizza.menucatalog.service.metadata.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final PizzaRepository pizzaRepository;
    private final RecipeMapper recipeMapper;

    @Override
    @Transactional
    @CacheEvict(value = "recipes", key = "#request.pizzaId")
    public RecipeResponse createRecipe(@Valid RecipeRequest request) {
        validatePizzaExists(request.getPizzaId());
        validateRecipeNotExists(request.getPizzaId());

        Recipe recipe = recipeMapper.toEntity(request);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("Created recipe for pizza ID: {}", request.getPizzaId());
        return recipeMapper.toResponse(savedRecipe);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "recipes", key = "#pizzaId")
    public RecipeResponse getRecipeByPizzaId(String pizzaId) {
        log.debug("Fetching recipe for pizza ID: {}", pizzaId);
        return recipeRepository.findByPizzaId(pizzaId)
                .map(recipeMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recipe not found for pizza id: " + pizzaId));
    }

    @Override
    @Transactional
    @CacheEvict(value = "recipes", key = "#pizzaId")
    public RecipeResponse updateRecipe(String pizzaId, @Valid RecipeRequest request) {
        Recipe existingRecipe = getExistingRecipe(pizzaId);
        validateRecipeConsistency(pizzaId, request);

        recipeMapper.updateEntity(request, existingRecipe);
        Recipe updatedRecipe = recipeRepository.save(existingRecipe);

        log.info("Updated recipe for pizza ID: {}", pizzaId);
        return recipeMapper.toResponse(updatedRecipe);
    }

    @Override
    @Transactional
    @CacheEvict(value = "recipes", key = "#pizzaId")
    public void deleteRecipe(String pizzaId) {
        Recipe recipe = getExistingRecipe(pizzaId);
        recipeRepository.delete(recipe);
        log.info("Deleted recipe for pizza ID: {}", pizzaId);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private Recipe getExistingRecipe(String pizzaId) {
        return recipeRepository.findByPizzaId(pizzaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recipe not found for pizza id: " + pizzaId));
    }

    private void validatePizzaExists(String pizzaId) {
        if (!pizzaRepository.existsById(pizzaId)) {
            throw new ResourceNotFoundException("Pizza not found with id: " + pizzaId);
        }
    }

    private void validateRecipeNotExists(String pizzaId) {
        if (recipeRepository.existsByPizzaId(pizzaId)) {
            throw new DuplicateResourceException(
                    "Recipe already exists for pizza id: " + pizzaId);
        }
    }

    private void validateRecipeConsistency(String pizzaId, RecipeRequest request) {
        if (!pizzaId.equals(request.getPizzaId())) {
            throw new IllegalArgumentException(
                    "Cannot change pizzaId for an existing recipe");
        }
    }
}