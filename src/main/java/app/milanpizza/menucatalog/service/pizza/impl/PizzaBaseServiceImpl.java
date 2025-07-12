package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.pizza.PizzaBase;
import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.PizzaBaseMapper;
import app.milanpizza.menucatalog.repository.pizza.PizzaBaseRepository;
import app.milanpizza.menucatalog.service.pizza.PizzaBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PizzaBaseServiceImpl implements PizzaBaseService {

    private final PizzaBaseRepository pizzaBaseRepository;
    private final PizzaBaseMapper pizzaBaseMapper;

    @Override
    @Transactional
    @CacheEvict(value = "pizzaBases", allEntries = true)
    public PizzaBaseResponse createPizzaBase(PizzaBaseRequest request) {
        validatePizzaBaseDoesNotExist(request.getName());

        PizzaBase pizzaBase = pizzaBaseMapper.toEntity(request);
        PizzaBase savedBase = pizzaBaseRepository.save(pizzaBase);

        log.info("Created new pizza base with ID: {}", savedBase.getBaseId());
        return pizzaBaseMapper.toResponse(savedBase);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "pizzaBases", key = "#id")
    public PizzaBaseResponse getPizzaBaseById(String id) {
        log.debug("Fetching pizza base with ID: {}", id);
        PizzaBase pizzaBase = findPizzaBaseById(id);
        return pizzaBaseMapper.toResponse(pizzaBase);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("pizzaBases")
    public List<PizzaBaseResponse> getAllPizzaBases() {
        return pizzaBaseRepository.findAll().stream()
                .map(pizzaBaseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaBaseResponse> getPizzaBasesByTexture(String texture) {
        validateTexture(texture);
        return pizzaBaseRepository.findByTexture(texture).stream()
                .map(pizzaBaseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzaBases", key = "#id")
    public PizzaBaseResponse updatePizzaBase(String id, PizzaBaseRequest request) {
        PizzaBase existingBase = findPizzaBaseById(id);
        pizzaBaseMapper.updateEntity(request, existingBase);

        PizzaBase updatedBase = pizzaBaseRepository.save(existingBase);
        log.info("Updated pizza base with ID: {}", id);
        return pizzaBaseMapper.toResponse(updatedBase);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzaBases", key = "#id")
    public void deletePizzaBase(String id) {
        if (!pizzaBaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza base not found with id: " + id);
        }

        pizzaBaseRepository.deleteById(id);
        log.info("Deleted pizza base with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private PizzaBase findPizzaBaseById(String id) {
        return pizzaBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza base not found with id: " + id));
    }

    private void validatePizzaBaseDoesNotExist(String name) {
        if (pizzaBaseRepository.existsByName(name)) {
            throw new DuplicateResourceException("Pizza base with name '" + name + "' already exists");
        }
    }

    private void validateTexture(String texture) {
        if (texture == null || texture.isBlank()) {
            throw new IllegalArgumentException("Texture must not be null or empty");
        }
    }
}