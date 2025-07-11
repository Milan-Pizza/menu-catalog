package app.milanpizza.menucatalog.service.metadata.impl;

import app.milanpizza.menucatalog.domain.metadata.NutritionalInfo;
import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.NutritionMapper;
import app.milanpizza.menucatalog.repository.metadata.NutritionalInfoRepository;
import app.milanpizza.menucatalog.service.metadata.NutritionalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NutritionalInfoServiceImpl implements NutritionalInfoService {

    private final NutritionalInfoRepository nutritionalInfoRepository;
    private final NutritionMapper nutritionMapper;

    @Override
    @Transactional(readOnly = true)
    public NutritionalInfoResponse getNutritionalInfoByItem(String itemId, String itemType) {
        NutritionalInfo nutritionalInfo = nutritionalInfoRepository.findByItemIdAndItemType(itemId, itemType);
        if (nutritionalInfo == null) {
            throw new ResourceNotFoundException("Nutritional info not found for item id: " + itemId + " and type: " + itemType);
        }
        return nutritionMapper.toResponse(nutritionalInfo);
    }
}