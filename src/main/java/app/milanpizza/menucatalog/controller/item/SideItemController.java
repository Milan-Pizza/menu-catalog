package app.milanpizza.menucatalog.controller.item;

import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.service.item.SideItemService;
import app.milanpizza.menucatalog.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/side-items")
@RequiredArgsConstructor
public class SideItemController extends BaseController {

    private final SideItemService sideItemService;

    @PostMapping
    public ResponseEntity<SideItemResponse> createSideItem(
            @Valid @RequestBody CreateSideItemRequest request) {
        SideItemResponse response = sideItemService.createSideItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SideItemResponse> getSideItemById(@PathVariable String id) {
        SideItemResponse response = sideItemService.getSideItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<SideItemResponse>> getSideItemsByMenu(
            @PathVariable String menuId) {
        List<SideItemResponse> response = sideItemService.getAllSideItemsByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}/available")
    public ResponseEntity<List<SideItemResponse>> getAvailableSideItemsByMenu(
            @PathVariable String menuId) {
        List<SideItemResponse> response = sideItemService.getAvailableSideItemsByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SideItemResponse> updateSideItem(
            @PathVariable String id,
            @Valid @RequestBody UpdateSideItemRequest request) {
        SideItemResponse response = sideItemService.updateSideItem(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<Void> toggleSideItemAvailability(@PathVariable String id) {
        sideItemService.toggleSideItemAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSideItem(@PathVariable String id) {
        sideItemService.deleteSideItem(id);
        return ResponseEntity.noContent().build();
    }
}