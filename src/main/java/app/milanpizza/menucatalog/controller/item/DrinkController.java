package app.milanpizza.menucatalog.controller.item;

import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.service.item.DrinkService;
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
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
@Tag(name = "Drink Management", description = "Endpoints for managing drink items")
public class DrinkController {

    private final DrinkService drinkService;

    @PostMapping
    @Operation(summary = "Create a new drink",
            description = "Creates a new drink item in the system")
    @ApiResponse(responseCode = "201", description = "Drink created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "Drink with this name already exists")
    public ResponseEntity<DrinkResponse> createDrink(
            @Valid @RequestBody CreateDrinkRequest request) {
        DrinkResponse response = drinkService.createDrink(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
//                .header("Location", "/api/drinks/" + response.getId())
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get drink by ID",
            description = "Returns a specific drink by its identifier")
    @ApiResponse(responseCode = "200", description = "Drink found")
    @ApiResponse(responseCode = "404", description = "Drink not found")
    public ResponseEntity<DrinkResponse> getDrinkById(
            @Parameter(description = "ID of the drink to be retrieved")
            @PathVariable String id) {
        DrinkResponse response = drinkService.getDrinkById(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
//                .eTag(response.getVersion().toString())
                .body(response);
    }

    @GetMapping("/menu/{menuId}")
    @Operation(summary = "Get all drinks by menu",
            description = "Returns all drinks associated with a specific menu")
    @ApiResponse(responseCode = "200", description = "List of drinks retrieved")
    public ResponseEntity<List<DrinkResponse>> getDrinksByMenu(
            @Parameter(description = "ID of the menu to filter drinks")
            @PathVariable String menuId) {
        List<DrinkResponse> response = drinkService.getAllDrinksByMenu(menuId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(response);
    }

    @GetMapping("/menu/{menuId}/available")
    @Operation(summary = "Get available drinks by menu",
            description = "Returns available drinks associated with a specific menu")
    @ApiResponse(responseCode = "200", description = "List of available drinks retrieved")
    public ResponseEntity<List<DrinkResponse>> getAvailableDrinksByMenu(
            @Parameter(description = "ID of the menu to filter available drinks")
            @PathVariable String menuId) {
        List<DrinkResponse> response = drinkService.getAvailableDrinksByMenu(menuId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                .body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update drink",
            description = "Updates an existing drink item")
    @ApiResponse(responseCode = "200", description = "Drink updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Drink not found")
    @ApiResponse(responseCode = "409", description = "Conflict with existing data")
    public ResponseEntity<DrinkResponse> updateDrink(
            @Parameter(description = "ID of the drink to be updated")
            @PathVariable String id,
            @Valid @RequestBody UpdateDrinkRequest request) {
        DrinkResponse response = drinkService.updateDrink(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-availability")
    @Operation(summary = "Toggle drink availability",
            description = "Toggles the availability status of a drink")
    @ApiResponse(responseCode = "204", description = "Availability toggled successfully")
    @ApiResponse(responseCode = "404", description = "Drink not found")
    public ResponseEntity<Void> toggleDrinkAvailability(
            @Parameter(description = "ID of the drink to toggle availability")
            @PathVariable String id) {
        drinkService.toggleDrinkAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete drink",
            description = "Deletes a drink from the system")
    @ApiResponse(responseCode = "204", description = "Drink deleted successfully")
    @ApiResponse(responseCode = "404", description = "Drink not found")
    public ResponseEntity<Void> deleteDrink(
            @Parameter(description = "ID of the drink to be deleted")
            @PathVariable String id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }
}