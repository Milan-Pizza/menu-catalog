package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import app.milanpizza.menucatalog.service.pizza.ToppingService;
import app.milanpizza.menucatalog.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toppings")
@RequiredArgsConstructor
public class ToppingController extends BaseController {

    private final ToppingService toppingService;

    @PostMapping
    public ResponseEntity<AllowedToppingResponse> createTopping(
            @Valid @RequestBody ToppingRequest request) {
        AllowedToppingResponse response = toppingService.createTopping(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllowedToppingResponse> getToppingById(@PathVariable String id) {
        AllowedToppingResponse response = toppingService.getToppingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AllowedToppingResponse>> getAllToppings() {
        List<AllowedToppingResponse> response = toppingService.getAllToppings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<AllowedToppingResponse>> getToppingsByCategory(
            @PathVariable String category) {
        List<AllowedToppingResponse> response = toppingService.getToppingsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllowedToppingResponse> updateTopping(
            @PathVariable String id,
            @Valid @RequestBody ToppingRequest request) {
        AllowedToppingResponse response = toppingService.updateTopping(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopping(@PathVariable String id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.noContent().build();
    }
}