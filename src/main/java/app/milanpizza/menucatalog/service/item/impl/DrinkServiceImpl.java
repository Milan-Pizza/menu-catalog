package app.milanpizza.menucatalog.service.item.impl;

import app.milanpizza.menucatalog.domain.item.Drink;
import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.DrinkMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.item.DrinkRepository;
import app.milanpizza.menucatalog.service.item.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

    private final DrinkRepository drinkRepository;
    private final MenuRepository menuRepository;
    private final DrinkMapper drinkMapper;

    @Override
    @Transactional
    public DrinkResponse createDrink(CreateDrinkRequest request) {
        if (!menuRepository.existsById(request.getMenuId())) {
            throw new ResourceNotFoundException("Menu not found with id: " + request.getMenuId());
        }

        Drink drink = drinkMapper.toEntity(request);
        Drink savedDrink = drinkRepository.save(drink);
        return drinkMapper.toResponse(savedDrink);
    }

    @Override
    @Transactional(readOnly = true)
    public DrinkResponse getDrinkById(String id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));
        return drinkMapper.toResponse(drink);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrinkResponse> getAllDrinksByMenu(String menuId) {
        return drinkRepository.findByMenuId(menuId).stream()
                .map(drinkMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrinkResponse> getAvailableDrinksByMenu(String menuId) {
        return drinkRepository.findByMenuIdAndIsAvailable(menuId, true).stream()
                .map(drinkMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DrinkResponse updateDrink(String id, UpdateDrinkRequest request) {
        Drink existingDrink = drinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));

        drinkMapper.updateEntity(request, existingDrink);
        Drink updatedDrink = drinkRepository.save(existingDrink);
        return drinkMapper.toResponse(updatedDrink);
    }

    @Override
    @Transactional
    public void toggleDrinkAvailability(String id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));
        drink.setIsAvailable(!drink.getIsAvailable());
        drinkRepository.save(drink);
    }

    @Override
    @Transactional
    public void deleteDrink(String id) {
        if (!drinkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Drink not found with id: " + id);
        }
        drinkRepository.deleteById(id);
    }
}