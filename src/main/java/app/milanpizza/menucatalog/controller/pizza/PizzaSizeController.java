package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.config.PizzaSizeRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSizeResponse;
import app.milanpizza.menucatalog.service.pizza.PizzaSizeService;
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
@RequestMapping(value = "/api/pizza-sizes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Pizza Size Management", description = "Endpoints for managing pizza sizes")
public class PizzaSizeController {

    private final PizzaSizeService pizzaSizeService;

    @PostMapping
    @Operation(summary = "Create a new pizza size",
            description = "Creates a new pizza size with the given specifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pizza size created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Pizza size already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaSizeResponse> createPizzaSize(
            @Valid @RequestBody PizzaSizeRequest request) {
        PizzaSizeResponse response = pizzaSizeService.createPizzaSize(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pizza size by ID",
            description = "Retrieves pizza size details by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza size found"),
            @ApiResponse(responseCode = "404", description = "Pizza size not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaSizeResponse> getPizzaSizeById(
            @Parameter(description = "ID of the pizza size to retrieve", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        PizzaSizeResponse response = pizzaSizeService.getPizzaSizeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all pizza sizes",
            description = "Retrieves a list of all available pizza sizes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza sizes retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<PizzaSizeResponse>> getAllPizzaSizes() {
        List<PizzaSizeResponse> response = pizzaSizeService.getAllPizzaSizes();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update pizza size",
            description = "Updates an existing pizza size with new specifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza size updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Pizza size not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaSizeResponse> updatePizzaSize(
            @Parameter(description = "ID of the pizza size to update", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id,
            @Valid @RequestBody PizzaSizeRequest request) {
        PizzaSizeResponse response = pizzaSizeService.updatePizzaSize(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete pizza size",
            description = "Removes a pizza size from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pizza size deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza size not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deletePizzaSize(
            @Parameter(description = "ID of the pizza size to delete", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        pizzaSizeService.deletePizzaSize(id);
        return ResponseEntity.noContent().build();
    }
}