package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.pizza.PizzaSize;
import app.milanpizza.menucatalog.dto.request.config.PizzaSizeRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSizeResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.PizzaSizeMapper;
import app.milanpizza.menucatalog.repository.pizza.PizzaSizeRepository;
import app.milanpizza.menucatalog.service.pizza.PizzaSizeService;
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
public class PizzaSizeServiceImpl implements PizzaSizeService {

    private final PizzaSizeRepository pizzaSizeRepository;
    private final PizzaSizeMapper pizzaSizeMapper;

    @Override
    @Transactional
    @CacheEvict(value = "pizzaSizes", allEntries = true)
    public PizzaSizeResponse createPizzaSize(PizzaSizeRequest request) {
        validatePizzaSizeDoesNotExist(request.getName().toString());

        PizzaSize pizzaSize = pizzaSizeMapper.toEntity(request);
        PizzaSize savedPizzaSize = pizzaSizeRepository.save(pizzaSize);

        log.info("Created new pizza size with ID: {}", savedPizzaSize.getSizeId());
        return pizzaSizeMapper.toResponse(savedPizzaSize);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "pizzaSizes", key = "#id")
    public PizzaSizeResponse getPizzaSizeById(String id) {
        log.debug("Fetching pizza size with ID: {}", id);
        return pizzaSizeRepository.findById(id)
                .map(pizzaSizeMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza size not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("pizzaSizes")
    public List<PizzaSizeResponse> getAllPizzaSizes() {
        return pizzaSizeRepository.findAll().stream()
                .map(pizzaSizeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzaSizes", key = "#id")
    public PizzaSizeResponse updatePizzaSize(String id, PizzaSizeRequest request) {
        PizzaSize existingPizzaSize = findPizzaSizeById(id);
        pizzaSizeMapper.updateEntity(request, existingPizzaSize);

        PizzaSize updatedPizzaSize = pizzaSizeRepository.save(existingPizzaSize);
        log.info("Updated pizza size with ID: {}", id);
        return pizzaSizeMapper.toResponse(updatedPizzaSize);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pizzaSizes", key = "#id")
    public void deletePizzaSize(String id) {
        if (!pizzaSizeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza size not found with id: " + id);
        }

        pizzaSizeRepository.deleteById(id);
        log.info("Deleted pizza size with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private PizzaSize findPizzaSizeById(String id) {
        return pizzaSizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza size not found with id: " + id));
    }

    private void validatePizzaSizeDoesNotExist(String name) {
        if (pizzaSizeRepository.existsByName(name)) {
            throw new DuplicateResourceException("Pizza size with name '" + name + "' already exists");
        }
    }
}