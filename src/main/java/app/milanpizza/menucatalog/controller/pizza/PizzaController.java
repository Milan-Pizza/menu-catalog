package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.pizza.CreatePizzaRequest;
import app.milanpizza.menucatalog.dto.request.pizza.PizzaSizeConfigRequest;
import app.milanpizza.menucatalog.dto.request.pizza.PizzaToppingConfigRequest;
import app.milanpizza.menucatalog.dto.request.pizza.UpdatePizzaRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaDetailedResponse;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSummaryResponse;
import app.milanpizza.menucatalog.service.pizza.PizzaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
@RequiredArgsConstructor
public class PizzaController {

    private final PizzaService pizzaService;

    @PostMapping
    public ResponseEntity<PizzaDetailedResponse> createPizza(
            @Valid @RequestBody CreatePizzaRequest request) {
        PizzaDetailedResponse response = pizzaService.createPizza(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDetailedResponse> getPizzaById(@PathVariable String id) {
        PizzaDetailedResponse response = pizzaService.getPizzaById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<PizzaSummaryResponse>> getPizzasByMenu(
            @PathVariable String menuId) {
        List<PizzaSummaryResponse> response = pizzaService.getAllPizzasByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}/available")
    public ResponseEntity<List<PizzaSummaryResponse>> getAvailablePizzasByMenu(
            @PathVariable String menuId) {
        List<PizzaSummaryResponse> response = pizzaService.getAvailablePizzasByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaDetailedResponse> updatePizza(
            @PathVariable String id,
            @Valid @RequestBody UpdatePizzaRequest request) {
        PizzaDetailedResponse response = pizzaService.updatePizza(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<Void> togglePizzaAvailability(@PathVariable String id) {
        pizzaService.togglePizzaAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pizzaId}/sizes")
    public ResponseEntity<Void> addSizesToPizza(
            @PathVariable String pizzaId,
            @Valid @RequestBody PizzaSizeConfigRequest request) {
        pizzaService.addSizeToPizza(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{pizzaId}/toppings")
    public ResponseEntity<Void> addToppingToPizza(
            @PathVariable String pizzaId,
            @Valid @RequestBody PizzaToppingConfigRequest request) {
        pizzaService.addToppingToPizza(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{pizzaId}/toppings/{toppingId}")
    public ResponseEntity<Void> removeToppingFromPizza(
            @PathVariable String pizzaId,
            @PathVariable String toppingId) {
        pizzaService.removeToppingFromPizza(pizzaId, toppingId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable String id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}