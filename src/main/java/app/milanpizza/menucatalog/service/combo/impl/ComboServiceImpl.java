package app.milanpizza.menucatalog.service.combo.impl;

import app.milanpizza.menucatalog.domain.combo.ComboMeal;
import app.milanpizza.menucatalog.dto.request.combo.CreateComboRequest;
import app.milanpizza.menucatalog.dto.request.combo.UpdateComboRequest;
import app.milanpizza.menucatalog.dto.response.combo.ComboDetailedResponse;
import app.milanpizza.menucatalog.dto.response.combo.ComboSummaryResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.ComboMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.combo.ComboMealRepository;
import app.milanpizza.menucatalog.service.combo.ComboService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {

    private final ComboMealRepository comboMealRepository;
    private final MenuRepository menuRepository;
    private final ComboMapper comboMapper;

    @Override
    @Transactional
    public ComboDetailedResponse createCombo(CreateComboRequest request) {
        if (!menuRepository.existsById(request.getMenuId())) {
            throw new ResourceNotFoundException("Menu not found with id: " + request.getMenuId());
        }

        ComboMeal combo = comboMapper.toEntity(request);
        combo.setIsActive(true);
        ComboMeal savedCombo = comboMealRepository.save(combo);
        return comboMapper.toDetailedResponse(savedCombo);
    }

    @Override
    @Transactional(readOnly = true)
    public ComboDetailedResponse getComboById(String id) {
        ComboMeal combo = comboMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));
        return comboMapper.toDetailedResponse(combo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboSummaryResponse> getAllCombosByMenu(String menuId) {
        return comboMealRepository.findByMenuId(menuId).stream()
                .map(comboMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboSummaryResponse> getActiveCombosByMenu(String menuId) {
        return comboMealRepository.findActiveCombosByMenu(menuId).stream()
                .map(comboMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    public ComboDetailedResponse updateCombo(String id, UpdateComboRequest request) {
        ComboMeal existingCombo = comboMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));

        comboMapper.updateEntity(request, existingCombo);
        ComboMeal updatedCombo = comboMealRepository.save(existingCombo);
        return comboMapper.toDetailedResponse(updatedCombo);
    }

    @Override
    @Transactional
    public void toggleComboAvailability(String id) {
        ComboMeal combo = comboMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));
        combo.setIsActive(!combo.getIsActive());
        comboMealRepository.save(combo);
    }

    @Override
    @Transactional
    public void deleteCombo(String id) {
        if (!comboMealRepository.existsById(id)) {
            throw new ResourceNotFoundException("Combo not found with id: " + id);
        }
        comboMealRepository.deleteById(id);
    }
}