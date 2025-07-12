package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.config.PizzaSizeRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaSizeResponse;
import app.milanpizza.menucatalog.service.pizza.PizzaSizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizza-sizes")
@RequiredArgsConstructor
public class PizzaSizeController {

    private final PizzaSizeService pizzaSizeService;

    @PostMapping
    public ResponseEntity<PizzaSizeResponse> createPizzaSize(@Valid @RequestBody PizzaSizeRequest request) {
        PizzaSizeResponse response = pizzaSizeService.createPizzaSize(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaSizeResponse> getPizzaSizeById(@PathVariable String id) {
        PizzaSizeResponse response = pizzaSizeService.getPizzaSizeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PizzaSizeResponse>> getAllPizzaSizes() {
        List<PizzaSizeResponse> response = pizzaSizeService.getAllPizzaSizes();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaSizeResponse> updatePizzaSize(
            @PathVariable String id,
            @Valid @RequestBody PizzaSizeRequest request) {
        PizzaSizeResponse response = pizzaSizeService.updatePizzaSize(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizzaSize(@PathVariable String id) {
        pizzaSizeService.deletePizzaSize(id);
        return ResponseEntity.noContent().build();
    }

}
