package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.pizza.*;
import app.milanpizza.menucatalog.dto.response.pizza.*;
import app.milanpizza.menucatalog.service.pizza.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(value = "/api/pizzas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Pizza Management", description = "Endpoints for managing pizzas in the menu")
public class PizzaController {

    private final PizzaService pizzaService;

    @PostMapping
    @Operation(summary = "Create a new pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pizza created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Pizza already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaDetailedResponse> createPizza(
            @Valid @RequestBody CreatePizzaRequest request) {
        PizzaDetailedResponse response = pizzaService.createPizza(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pizza by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza found"),
            @ApiResponse(responseCode = "404", description = "Pizza not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaDetailedResponse> getPizzaById(
            @Parameter(description = "ID of pizza to be retrieved", required = true)
            @PathVariable String id) {
        PizzaDetailedResponse response = pizzaService.getPizzaById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}")
    @Operation(summary = "Get all pizzas by menu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizzas retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<PizzaSummaryResponse>> getPizzasByMenu(
            @Parameter(description = "ID of menu to filter pizzas", required = true)
            @PathVariable String menuId) {
        List<PizzaSummaryResponse> response = pizzaService.getAllPizzasByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}/available")
    @Operation(summary = "Get available pizzas by menu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available pizzas retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<PizzaSummaryResponse>> getAvailablePizzasByMenu(
            @Parameter(description = "ID of menu to filter available pizzas", required = true)
            @PathVariable String menuId) {
        List<PizzaSummaryResponse> response = pizzaService.getAvailablePizzasByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update pizza by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Pizza not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaDetailedResponse> updatePizza(
            @Parameter(description = "ID of pizza to be updated", required = true)
            @PathVariable String id,
            @Valid @RequestBody UpdatePizzaRequest request) {
        PizzaDetailedResponse response = pizzaService.updatePizza(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/availability")
    @Operation(summary = "Toggle pizza availability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pizza availability toggled successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> togglePizzaAvailability(
            @Parameter(description = "ID of pizza to toggle availability", required = true)
            @PathVariable String id) {
        pizzaService.togglePizzaAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pizzaId}/sizes")
    @Operation(summary = "Add size configuration to pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Size configuration added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Pizza or size not found"),
            @ApiResponse(responseCode = "409", description = "Size already configured for pizza"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> addSizeToPizza(
            @Parameter(description = "ID of pizza to configure size for", required = true)
            @PathVariable String pizzaId,
            @Valid @RequestBody PizzaSizeConfigRequest request) {
        pizzaService.addSizeToPizza(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{pizzaId}/toppings")
    @Operation(summary = "Add topping configuration to pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Topping configuration added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Pizza or topping not found"),
            @ApiResponse(responseCode = "409", description = "Topping already configured for pizza"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> addToppingToPizza(
            @Parameter(description = "ID of pizza to configure topping for", required = true)
            @PathVariable String pizzaId,
            @Valid @RequestBody PizzaToppingConfigRequest request) {
        pizzaService.addToppingToPizza(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{pizzaId}/toppings/{toppingId}")
    @Operation(summary = "Remove topping configuration from pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Topping configuration removed successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza or topping configuration not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> removeToppingFromPizza(
            @Parameter(description = "ID of pizza to remove topping from", required = true)
            @PathVariable String pizzaId,
            @Parameter(description = "ID of topping to remove", required = true)
            @PathVariable String toppingId) {
        pizzaService.removeToppingFromPizza(pizzaId, toppingId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete pizza by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pizza deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deletePizza(
            @Parameter(description = "ID of pizza to be deleted", required = true)
            @PathVariable String id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}