package app.milanpizza.menucatalog.controller;

import app.milanpizza.menucatalog.dto.request.menu.CreateMenuRequest;
import app.milanpizza.menucatalog.dto.request.menu.UpdateMenuRequest;
import app.milanpizza.menucatalog.dto.response.menu.MenuDetailedResponse;
import app.milanpizza.menucatalog.dto.response.menu.MenuSummaryResponse;
import app.milanpizza.menucatalog.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/menus", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Menu Management", description = "Endpoints for managing restaurant menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    @Operation(summary = "Create a new menu", description = "Creates a new menu with the specified configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Menu with this name already exists in the region"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<MenuSummaryResponse> createMenu(
            @Valid @RequestBody CreateMenuRequest request) {
        MenuSummaryResponse response = menuService.createMenu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu by ID", description = "Retrieves complete menu details including all associated items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu found"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<MenuDetailedResponse> getMenuById(
            @Parameter(description = "ID of the menu to retrieve", required = true, example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        MenuDetailedResponse response = menuService.getMenuById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all menus", description = "Retrieves a list of all menus in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<MenuSummaryResponse>> getAllMenus() {
        List<MenuSummaryResponse> response = menuService.getAllMenus();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/region/{regionCode}")
    @Operation(summary = "Get active menus by region", description = "Retrieves all active menus for a specific region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active menus retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid region code"),
            @ApiResponse(responseCode = "404", description = "No active menus found for region"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<MenuSummaryResponse>> getActiveMenusByRegion(
            @Parameter(description = "Region code to filter menus", required = true, example = "NY")
            @PathVariable String regionCode) {
        List<MenuSummaryResponse> response = menuService.getActiveMenusByRegion(regionCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get menus by date range",
            description = "Retrieves menus active within the specified date range. If end date is not provided, defaults to start date + 3 months")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<MenuSummaryResponse>> getMenusByDateRange(
            @Parameter(description = "Start date of range (inclusive)", required = true, example = "2023-01-01")
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(description = "End date of range (inclusive). Defaults to start date + 3 months if not provided",
                    example = "2023-03-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        LocalDate effectiveEndDate = endDate != null ? endDate : startDate.plusMonths(3);

        if (effectiveEndDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        List<MenuSummaryResponse> response = menuService.getMenusByDateRange(startDate, effectiveEndDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update menu", description = "Updates an existing menu with new configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<MenuDetailedResponse> updateMenu(
            @Parameter(description = "ID of the menu to update", required = true, example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id,
            @Valid @RequestBody UpdateMenuRequest request) {
        MenuDetailedResponse response = menuService.updateMenu(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate menu", description = "Sets a menu's status to active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu activated successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> activateMenu(
            @Parameter(description = "ID of the menu to activate", required = true, example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        menuService.activateMenu(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate menu", description = "Sets a menu's status to inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deactivateMenu(
            @Parameter(description = "ID of the menu to deactivate", required = true, example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        menuService.deactivateMenu(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete menu", description = "Permanently removes a menu from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteMenu(
            @Parameter(description = "ID of the menu to delete", required = true, example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}