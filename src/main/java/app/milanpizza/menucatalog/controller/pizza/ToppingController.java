package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.ToppingResponse;
import app.milanpizza.menucatalog.exception.BadRequestException;
import app.milanpizza.menucatalog.service.pizza.ToppingService;
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

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/toppings", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Topping Management", description = "Endpoints for managing pizza toppings")
public class ToppingController {

    private final ToppingService toppingService;

    @PostMapping
    @Operation(summary = "Create a new topping",
            description = "Creates a new pizza topping with specified properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Topping created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Topping with this name already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ToppingResponse> createTopping(
            @Valid @RequestBody ToppingRequest request) {
        ToppingResponse response = toppingService.createTopping(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get topping by ID",
            description = "Retrieves topping details by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topping found"),
            @ApiResponse(responseCode = "404", description = "Topping not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ToppingResponse> getToppingById(
            @Parameter(description = "ID of the topping to retrieve", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        ToppingResponse response = toppingService.getToppingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all toppings",
            description = "Retrieves a list of all available toppings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Toppings retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ToppingResponse>> getAllToppings() {
        List<ToppingResponse> response = toppingService.getAllToppings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get toppings by category",
            description = "Retrieves toppings filtered by specified category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Toppings retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ToppingResponse>> getToppingsByCategory(
            @Parameter(description = "Category to filter toppings", required = true,
                    schema = @Schema(implementation = ToppingCategory.class,
                            allowableValues = {"CHEESE", "MEAT", "VEGETABLE", "SAUCE", "SPICE"}))
            @PathVariable String category) {
        validateToppingCategory(category);
        List<ToppingResponse> response = toppingService.getToppingsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update topping",
            description = "Updates an existing topping with new properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topping updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Topping not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ToppingResponse> updateTopping(
            @Parameter(description = "ID of the topping to update", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id,
            @Valid @RequestBody ToppingRequest request) {
        ToppingResponse response = toppingService.updateTopping(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete topping",
            description = "Removes a topping from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Topping deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Topping not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteTopping(
            @Parameter(description = "ID of the topping to delete", required = true,
                    example = "64a1b8f3d8b7a12b3c4d5e6f")
            @PathVariable String id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.noContent().build();
    }

    private void validateToppingCategory(String category) {
        try {
            ToppingCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category. Allowed values: " + Arrays.toString(ToppingCategory.values()));
        }
    }
}