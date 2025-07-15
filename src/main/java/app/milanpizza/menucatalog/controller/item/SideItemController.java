package app.milanpizza.menucatalog.controller.item;

import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
import app.milanpizza.menucatalog.service.item.SideItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/side-items", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Side Item Management", description = "Endpoints for managing menu side items")
public class SideItemController {

    private final SideItemService sideItemService;

    @PostMapping
    @Operation(summary = "Create a new side item",
            description = "Creates a new side item with the specified properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Side item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "409", description = "Side item with this name already exists in the menu"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SideItemResponse> createSideItem(
            @Valid @RequestBody CreateSideItemRequest request) {
        SideItemResponse response = sideItemService.createSideItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get side item by ID",
            description = "Retrieves side item details by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Side item found"),
            @ApiResponse(responseCode = "404", description = "Side item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SideItemResponse> getSideItemById(
            @Parameter(description = "ID of the side item to retrieve", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        SideItemResponse response = sideItemService.getSideItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}")
    @Operation(summary = "Get side items by menu",
            description = "Retrieves all side items associated with a specific menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Side items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<SideItemResponse>> getSideItemsByMenu(
            @Parameter(description = "ID of the menu to filter side items", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String menuId) {
        List<SideItemResponse> response = sideItemService.getAllSideItemsByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}/available")
    @Operation(summary = "Get available side items by menu",
            description = "Retrieves all available side items associated with a specific menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available side items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<SideItemResponse>> getAvailableSideItemsByMenu(
            @Parameter(description = "ID of the menu to filter available side items", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String menuId) {
        List<SideItemResponse> response = sideItemService.getAvailableSideItemsByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update side item",
            description = "Updates an existing side item with new properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Side item updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Side item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SideItemResponse> updateSideItem(
            @Parameter(description = "ID of the side item to update", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id,
            @Valid @RequestBody UpdateSideItemRequest request) {
        SideItemResponse response = sideItemService.updateSideItem(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/availability")
    @Operation(summary = "Toggle side item availability",
            description = "Toggles the availability status of a side item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Side item availability toggled successfully"),
            @ApiResponse(responseCode = "404", description = "Side item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> toggleSideItemAvailability(
            @Parameter(description = "ID of the side item to toggle availability", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        sideItemService.toggleSideItemAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete side item",
            description = "Permanently removes a side item from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Side item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Side item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteSideItem(
            @Parameter(description = "ID of the side item to delete", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        sideItemService.deleteSideItem(id);
        return ResponseEntity.noContent().build();
    }
}