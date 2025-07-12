package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.ToppingResponse;
import app.milanpizza.menucatalog.exception.BadRequestException;
import app.milanpizza.menucatalog.service.pizza.ToppingService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/toppings")
@RequiredArgsConstructor
public class ToppingController {

    private final ToppingService toppingService;

    @PostMapping
    public ResponseEntity<ToppingResponse> createTopping(@Valid @RequestBody ToppingRequest request) {
        ToppingResponse response = toppingService.createTopping(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToppingResponse> getToppingById(@PathVariable String id) {
        ToppingResponse response = toppingService.getToppingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ToppingResponse>> getAllToppings() {
        List<ToppingResponse> response = toppingService.getAllToppings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ToppingResponse>> getToppingsByCategory(
            @PathVariable @Schema(description = "Topping category", implementation = ToppingCategory.class)
            String category) {
        // Validate that category is a valid enum value
        try {
            ToppingCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category. Allowed values: " + Arrays.toString(ToppingCategory.values()));
        }

        List<ToppingResponse> response = toppingService.getToppingsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToppingResponse> updateTopping(
            @PathVariable String id,
            @Valid @RequestBody ToppingRequest request) {
        ToppingResponse response = toppingService.updateTopping(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopping(@PathVariable String id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.noContent().build();
    }
}