package app.milanpizza.menucatalog.controller;

import app.milanpizza.menucatalog.dto.request.menu.CreateMenuRequest;
import app.milanpizza.menucatalog.dto.request.menu.UpdateMenuRequest;
import app.milanpizza.menucatalog.dto.response.menu.MenuDetailedResponse;
import app.milanpizza.menucatalog.dto.response.menu.MenuSummaryResponse;
import app.milanpizza.menucatalog.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuSummaryResponse> createMenu(@Valid @RequestBody CreateMenuRequest request) {
        MenuSummaryResponse response = menuService.createMenu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDetailedResponse> getMenuById(@PathVariable String id) {
        MenuDetailedResponse response = menuService.getMenuById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuSummaryResponse>> getAllMenus() {
        List<MenuSummaryResponse> response = menuService.getAllMenus();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/region/{regionCode}")
    public ResponseEntity<List<MenuSummaryResponse>> getActiveMenusByRegion(
            @PathVariable String regionCode) {
        List<MenuSummaryResponse> response = menuService.getActiveMenusByRegion(regionCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<MenuSummaryResponse>> getMenusByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // Default: if endDate not provided, use startDate + 3 months
        LocalDate effectiveEndDate = endDate != null ? endDate : startDate.plusMonths(3);

        List<MenuSummaryResponse> response = menuService.getMenusByDateRange(startDate, effectiveEndDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDetailedResponse> updateMenu(
            @PathVariable String id,
            @Valid @RequestBody UpdateMenuRequest request) {
        MenuDetailedResponse response = menuService.updateMenu(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateMenu(@PathVariable String id) {
        menuService.activateMenu(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateMenu(@PathVariable String id) {
        menuService.deactivateMenu(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}