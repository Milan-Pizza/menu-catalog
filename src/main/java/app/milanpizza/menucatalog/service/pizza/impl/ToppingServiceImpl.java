package app.milanpizza.menucatalog.service.pizza.impl;

import app.milanpizza.menucatalog.domain.pizza.Topping;
import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.ToppingMapper;
import app.milanpizza.menucatalog.repository.pizza.ToppingRepository;
import app.milanpizza.menucatalog.service.pizza.ToppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {

    private final ToppingRepository toppingRepository;
    private final ToppingMapper toppingMapper;

    @Override
    @Transactional
    public AllowedToppingResponse createTopping(ToppingRequest request) {
        Topping topping = toppingMapper.toEntity(request);
        Topping savedTopping = toppingRepository.save(topping);
        return toppingMapper.toAllowedToppingResponse(savedTopping);
    }

    @Override
    @Transactional(readOnly = true)
    public AllowedToppingResponse getToppingById(String id) {
        Topping topping = toppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topping not found with id: " + id));
        return toppingMapper.toAllowedToppingResponse(topping);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllowedToppingResponse> getAllToppings() {
        return toppingRepository.findAll().stream()
                .map(toppingMapper::toAllowedToppingResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllowedToppingResponse> getToppingsByCategory(String category) {
        return toppingRepository.findByCategory(category).stream()
                .map(toppingMapper::toAllowedToppingResponse)
                .toList();
    }

    @Override
    @Transactional
    public AllowedToppingResponse updateTopping(String id, ToppingRequest request) {
        Topping existingTopping = toppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topping not found with id: " + id));

        toppingMapper.updateEntity(request, existingTopping);
        Topping updatedTopping = toppingRepository.save(existingTopping);
        return toppingMapper.toAllowedToppingResponse(updatedTopping);
    }

    @Override
    @Transactional
    public void deleteTopping(String id) {
        if (!toppingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topping not found with id: " + id);
        }
        toppingRepository.deleteById(id);
    }
}