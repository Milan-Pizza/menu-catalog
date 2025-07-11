package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.pizza.PizzaBase;
import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.PizzaBaseMapper;
import app.milanpizza.menucatalog.repository.pizza.PizzaBaseRepository;
import app.milanpizza.menucatalog.service.pizza.PizzaBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaBaseServiceImpl implements PizzaBaseService {

    private final PizzaBaseRepository pizzaBaseRepository;
    private final PizzaBaseMapper pizzaBaseMapper;

    @Override
    @Transactional
    public PizzaBaseResponse createPizzaBase(PizzaBaseRequest request) {
        PizzaBase pizzaBase = pizzaBaseMapper.toEntity(request);
        PizzaBase savedBase = pizzaBaseRepository.save(pizzaBase);
        return pizzaBaseMapper.toResponse(savedBase);
    }

    @Override
    @Transactional(readOnly = true)
    public PizzaBaseResponse getPizzaBaseById(String id) {
        PizzaBase pizzaBase = pizzaBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza base not found with id: " + id));
        return pizzaBaseMapper.toResponse(pizzaBase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaBaseResponse> getAllPizzaBases() {
        return pizzaBaseRepository.findAll().stream()
                .map(pizzaBaseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PizzaBaseResponse> getPizzaBasesByTexture(String texture) {
        return pizzaBaseRepository.findByTexture(texture).stream()
                .map(pizzaBaseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PizzaBaseResponse updatePizzaBase(String id, PizzaBaseRequest request) {
        PizzaBase existingBase = pizzaBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza base not found with id: " + id));

        pizzaBaseMapper.updateEntity(request, existingBase);
        PizzaBase updatedBase = pizzaBaseRepository.save(existingBase);
        return pizzaBaseMapper.toResponse(updatedBase);
    }

    @Override
    @Transactional
    public void deletePizzaBase(String id) {
        if (!pizzaBaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza base not found with id: " + id);
        }
        pizzaBaseRepository.deleteById(id);
    }
}