package app.milanpizza.menucatalog.service.item.impl;

import app.milanpizza.menucatalog.domain.item.SideItem;
import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.exception.ResourceNotFoundException;
import app.milanpizza.menucatalog.mapper.SideItemMapper;
import app.milanpizza.menucatalog.repository.MenuRepository;
import app.milanpizza.menucatalog.repository.item.SideItemRepository;
import app.milanpizza.menucatalog.service.item.SideItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SideItemServiceImpl implements SideItemService {

    private final SideItemRepository sideItemRepository;
    private final MenuRepository menuRepository;
    private final SideItemMapper sideItemMapper;

    @Override
    @Transactional
    public SideItemResponse createSideItem(CreateSideItemRequest request) {
        if (!menuRepository.existsById(request.getMenuId())) {
            throw new ResourceNotFoundException("Menu not found with id: " + request.getMenuId());
        }

        SideItem sideItem = sideItemMapper.toEntity(request);
        SideItem savedItem = sideItemRepository.save(sideItem);
        return sideItemMapper.toResponse(savedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public SideItemResponse getSideItemById(String id) {
        SideItem sideItem = sideItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Side item not found with id: " + id));
        return sideItemMapper.toResponse(sideItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SideItemResponse> getAllSideItemsByMenu(String menuId) {
        return sideItemRepository.findByMenuId(menuId).stream()
                .map(sideItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SideItemResponse> getAvailableSideItemsByMenu(String menuId) {
        return sideItemRepository.findByMenuIdAndIsAvailable(menuId, true).stream()
                .map(sideItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SideItemResponse updateSideItem(String id, UpdateSideItemRequest request) {
        SideItem existingItem = sideItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Side item not found with id: " + id));

        sideItemMapper.updateEntity(request, existingItem);
        SideItem updatedItem = sideItemRepository.save(existingItem);
        return sideItemMapper.toResponse(updatedItem);
    }

    @Override
    @Transactional
    public void toggleSideItemAvailability(String id) {
        SideItem sideItem = sideItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Side item not found with id: " + id));
        sideItem.setIsAvailable(!sideItem.getIsAvailable());
        sideItemRepository.save(sideItem);
    }

    @Override
    @Transactional
    public void deleteSideItem(String id) {
        if (!sideItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Side item not found with id: " + id);
        }
        sideItemRepository.deleteById(id);
    }
}