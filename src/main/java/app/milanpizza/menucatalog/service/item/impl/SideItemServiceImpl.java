package app.milanpizza.menucatalog.service.item.impl;

import app.milanpizza.menucatalog.domain.item.SideItem;
import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.exception.DuplicateResourceException;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.SideItemMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.item.SideItemRepository;
import app.milanpizza.menucatalog.service.item.SideItemService;
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
public class SideItemServiceImpl implements SideItemService {

    private final SideItemRepository sideItemRepository;
    private final MenuRepository menuRepository;
    private final SideItemMapper sideItemMapper;

    @Override
    @Transactional
    @CacheEvict(value = {"sideItems", "availableSideItems"}, key = "#request.menuId")
    public SideItemResponse createSideItem(CreateSideItemRequest request) {
        validateMenuExists(request.getMenuId());
        validateSideItemNameUnique(request.getName(), request.getMenuId());

        SideItem sideItem = sideItemMapper.toEntity(request);
        SideItem savedItem = sideItemRepository.save(sideItem);

        log.info("Created new side item with ID: {} for menu: {}", savedItem.getSideId(), request.getMenuId());
        return sideItemMapper.toResponse(savedItem);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "sideItems", key = "#id")
    public SideItemResponse getSideItemById(String id) {
        log.debug("Fetching side item with ID: {}", id);
        return sideItemRepository.findById(id)
                .map(sideItemMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Side item not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "sideItems", key = "#menuId")
    public List<SideItemResponse> getAllSideItemsByMenu(String menuId) {
        validateMenuExists(menuId);
        return sideItemRepository.findByMenuId(menuId).stream()
                .map(sideItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "availableSideItems", key = "#menuId")
    public List<SideItemResponse> getAvailableSideItemsByMenu(String menuId) {
        validateMenuExists(menuId);
        return sideItemRepository.findByMenuIdAndIsAvailable(menuId, true).stream()
                .map(sideItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"sideItems", "availableSideItems"}, key = "#result.menuId")
    public SideItemResponse updateSideItem(String id, UpdateSideItemRequest request) {
        SideItem existingItem = findSideItemById(id);
        sideItemMapper.updateEntity(request, existingItem);

        SideItem updatedItem = sideItemRepository.save(existingItem);
        log.info("Updated side item with ID: {}", id);
        return sideItemMapper.toResponse(updatedItem);
    }

    @Override
    @Transactional
    @CacheEvict(value = "availableSideItems", key = "#result.menuId")
    public void toggleSideItemAvailability(String id) {
        SideItem sideItem = findSideItemById(id);
        sideItem.setIsAvailable(!sideItem.getIsAvailable());

        sideItemRepository.save(sideItem);
        log.info("Toggled availability for side item ID: {} to {}", id, sideItem.getIsAvailable());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"sideItems", "availableSideItems"}, key = "#result.menuId")
    public void deleteSideItem(String id) {
        SideItem sideItem = findSideItemById(id);
        sideItemRepository.deleteById(id);
        log.info("Deleted side item with ID: {}", id);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private SideItem findSideItemById(String id) {
        return sideItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Side item not found with id: " + id));
    }

    private void validateMenuExists(String menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new ResourceNotFoundException("Menu not found with id: " + menuId);
        }
    }

    private void validateSideItemNameUnique(String name, String menuId) {
        if (sideItemRepository.existsByNameAndMenuId(name, menuId)) {
            throw new DuplicateResourceException(
                    "Side item with name '" + name + "' already exists in menu: " + menuId);
        }
    }
}