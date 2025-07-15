package app.milanpizza.menucatalog.controller.combo;

import app.milanpizza.menucatalog.dto.request.combo.*;
import app.milanpizza.menucatalog.dto.response.combo.*;
import app.milanpizza.menucatalog.service.combo.ComboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/combos")
@RequiredArgsConstructor
@Tag(name = "Combo Management", description = "Endpoints for managing combo meals")
public class ComboController {

    private final ComboService comboService;

    @PostMapping
    @Operation(summary = "Create a new combo meal",
            description = "Creates a new combo meal in the system")
    @ApiResponse(responseCode = "201", description = "Combo created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Referenced resources not found")
    @ApiResponse(responseCode = "409", description = "Combo with this name already exists")
    public ResponseEntity<ComboDetailedResponse> createCombo(
            @Valid @RequestBody CreateComboRequest request) {
        ComboDetailedResponse response = comboService.createCombo(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
//                .header("Location", "/api/combos/" + response.getId())
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get combo by ID",
            description = "Returns a specific combo meal by its identifier")
    @ApiResponse(responseCode = "200", description = "Combo found")
    @ApiResponse(responseCode = "404", description = "Combo not found")
    public ResponseEntity<ComboDetailedResponse> getComboById(
            @Parameter(description = "ID of the combo to be retrieved")
            @PathVariable String id) {
        ComboDetailedResponse response = comboService.getComboById(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(response);
    }

    @GetMapping("/menu/{menuId}")
    @Operation(summary = "Get combos by menu",
            description = "Returns all combos associated with a specific menu")
    @ApiResponse(responseCode = "200", description = "List of combos retrieved")
    public ResponseEntity<List<ComboSummaryResponse>> getCombosByMenu(
            @Parameter(description = "ID of the menu to filter combos")
            @PathVariable String menuId) {
        List<ComboSummaryResponse> response = comboService.getAllCombosByMenu(menuId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(response);
    }

    @GetMapping("/menu/{menuId}/active")
    @Operation(summary = "Get active combos by menu",
            description = "Returns active combos associated with a specific menu")
    @ApiResponse(responseCode = "200", description = "List of active combos retrieved")
    public ResponseEntity<List<ComboSummaryResponse>> getActiveCombosByMenu(
            @Parameter(description = "ID of the menu to filter active combos")
            @PathVariable String menuId) {
        List<ComboSummaryResponse> response = comboService.getActiveCombosByMenu(menuId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                .body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update combo",
            description = "Updates an existing combo meal")
    @ApiResponse(responseCode = "200", description = "Combo updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Combo not found")
    public ResponseEntity<ComboDetailedResponse> updateCombo(
            @Parameter(description = "ID of the combo to be updated")
            @PathVariable String id,
            @Valid @RequestBody UpdateComboRequest request) {
        ComboDetailedResponse response = comboService.updateCombo(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-availability")
    @Operation(summary = "Toggle combo availability",
            description = "Toggles the availability status of a combo")
    @ApiResponse(responseCode = "204", description = "Availability toggled successfully")
    @ApiResponse(responseCode = "404", description = "Combo not found")
    public ResponseEntity<Void> toggleComboAvailability(
            @Parameter(description = "ID of the combo to toggle availability")
            @PathVariable String id) {
        comboService.toggleComboAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete combo",
            description = "Deletes a combo from the system")
    @ApiResponse(responseCode = "204", description = "Combo deleted successfully")
    @ApiResponse(responseCode = "404", description = "Combo not found")
    public ResponseEntity<Void> deleteCombo(
            @Parameter(description = "ID of the combo to be deleted")
            @PathVariable String id) {
        comboService.deleteCombo(id);
        return ResponseEntity.noContent().build();
    }

    // === COMBO ITEM MANAGEMENT ===
    @PostMapping("/{comboId}/items")
    @Operation(summary = "Add item to combo",
            description = "Adds a new item (pizza, side, or drink) to a combo")
    @ApiResponse(responseCode = "201", description = "Item added to combo successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Combo or item not found")
    @ApiResponse(responseCode = "409", description = "Item already exists in combo")
    public ResponseEntity<ComboItemResponse> addItemToCombo(
            @Parameter(description = "ID of the combo to add item to")
            @PathVariable String comboId,
            @Valid @RequestBody AddComboItemRequest request) {
        ComboItemResponse response = comboService.addItemToCombo(comboId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/combos/" + comboId + "/items/" + response.getItemId())
                .body(response);
    }

    @GetMapping("/{comboId}/items")
    @Operation(summary = "Get combo items",
            description = "Returns all items in a specific combo")
    @ApiResponse(responseCode = "200", description = "List of combo items retrieved")
    @ApiResponse(responseCode = "404", description = "Combo not found")
    public ResponseEntity<List<ComboItemResponse>> getComboItems(
            @Parameter(description = "ID of the combo to get items from")
            @PathVariable String comboId) {
        List<ComboItemResponse> response = comboService.getComboItems(comboId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(response);
    }

    @PatchMapping("/{comboId}/items/{itemId}")
    @Operation(summary = "Update combo item",
            description = "Updates an item within a combo")
    @ApiResponse(responseCode = "200", description = "Combo item updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Combo or item not found")
    public ResponseEntity<ComboItemResponse> updateComboItem(
            @Parameter(description = "ID of the combo containing the item")
            @PathVariable String comboId,
            @Parameter(description = "ID of the item to update")
            @PathVariable String itemId,
            @Valid @RequestBody UpdateComboItemRequest request) {
        ComboItemResponse response = comboService.updateComboItem(comboId, itemId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{comboId}/items/{itemId}")
    @Operation(summary = "Remove item from combo",
            description = "Removes an item from a combo")
    @ApiResponse(responseCode = "204", description = "Item removed successfully")
    @ApiResponse(responseCode = "404", description = "Combo or item not found")
    public ResponseEntity<Void> removeItemFromCombo(
            @Parameter(description = "ID of the combo to remove item from")
            @PathVariable String comboId,
            @Parameter(description = "ID of the item to remove")
            @PathVariable String itemId) {
        comboService.removeItemFromCombo(comboId, itemId);
        return ResponseEntity.noContent().build();
    }
}