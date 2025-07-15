package app.milanpizza.menucatalog.service.metadata.impl;

import app.milanpizza.menucatalog.domain.enums.ItemType;
import app.milanpizza.menucatalog.domain.metadata.NutritionalInfo;
import app.milanpizza.menucatalog.dto.request.metadata.NutritionalInfoRequest;
import app.milanpizza.menucatalog.dto.response.metadata.NutritionalInfoResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.InvalidItemTypeException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.NutritionalInfoMapper;
import app.milanpizza.menucatalog.repository.metadata.NutritionalInfoRepository;
import app.milanpizza.menucatalog.service.metadata.NutritionalInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NutritionalInfoServiceImpl implements NutritionalInfoService {

    private final NutritionalInfoRepository nutritionalInfoRepository;
    private final NutritionalInfoMapper nutritionMapper;

    @Override
    @Transactional
    @CacheEvict(value = "nutritionalInfo", key = "{#request.itemId, #request.itemType}")
    public NutritionalInfoResponse createNutritionalInfo(NutritionalInfoRequest request) {
        validateItemType(request.getItemType());
        validateNutritionalInfoNotExists(request.getItemId(), request.getItemType());

        NutritionalInfo nutritionalInfo = nutritionMapper.toEntity(request);
        NutritionalInfo savedInfo = nutritionalInfoRepository.save(nutritionalInfo);

        log.info("Created nutritional info for item {} of type {}",
                request.getItemId(), request.getItemType());
        return nutritionMapper.toResponse(savedInfo);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "nutritionalInfo", key = "{#itemId, #itemType}")
    public NutritionalInfoResponse getNutritionalInfoByItem(String itemId, ItemType itemType) {
        log.debug("Fetching nutritional info for item: {} of type: {}", itemId, itemType);
        return nutritionalInfoRepository.findByItemIdAndItemType(itemId, String.valueOf(itemType))
                .map(nutritionMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Nutritional info not found for item %s of type %s",
                                itemId, itemType)));
    }

    @Override
    @Transactional
    @CacheEvict(value = "nutritionalInfo", key = "{#request.itemId, #request.itemType}")
    public NutritionalInfoResponse updateNutritionalInfo(String id, NutritionalInfoRequest request) {
        NutritionalInfo existingInfo = nutritionalInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nutritional info not found with id: " + id));

        nutritionMapper.updateEntity(request, existingInfo);
        NutritionalInfo updatedInfo = nutritionalInfoRepository.save(existingInfo);

        log.info("Updated nutritional info with ID: {}", id);
        return nutritionMapper.toResponse(updatedInfo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "nutritionalInfo", key = "{#result.itemId, #result.itemType}")
    public void deleteNutritionalInfo(String id) {
        NutritionalInfo nutritionalInfo = (NutritionalInfo) nutritionalInfoRepository.findByItemId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nutritional info not found with id: " + id));

        nutritionalInfoRepository.delete(nutritionalInfo);
        log.info("Deleted nutritional info with ID: {} for item {} of type {}",
                id, nutritionalInfo.getItemId(), nutritionalInfo.getItemType());
    }

    // ========== PRIVATE HELPER METHODS ==========

    private void validateItemType(ItemType itemType) {
        if (itemType == null) {
            throw new InvalidItemTypeException(
                    "Item type cannot be null. Valid types are: " +
                            Arrays.stream(ItemType.values())
                                    .map(Enum::name)
                                    .collect(Collectors.joining(", ")));
        }
    }

    private void validateNutritionalInfoNotExists(String itemId, ItemType itemType) {
        if (nutritionalInfoRepository.existsByItemIdAndItemType(itemId, String.valueOf(itemType))) {
            throw new DuplicateResourceException(
                    String.format("Nutritional info already exists for item %s of type %s",
                            itemId, itemType));
        }
    }
}