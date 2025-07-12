package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import app.milanpizza.menucatalog.service.pizza.PizzaBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizza-bases")
@RequiredArgsConstructor
public class PizzaGlobalExceptionHandler {

    private final PizzaBaseService pizzaBaseService;

    @PostMapping
    public ResponseEntity<PizzaBaseResponse> createPizzaBase(@Valid @RequestBody PizzaBaseRequest request) {
        PizzaBaseResponse response = pizzaBaseService.createPizzaBase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaBaseResponse> getPizzaBaseById(@PathVariable String id) {
        PizzaBaseResponse response = pizzaBaseService.getPizzaBaseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PizzaBaseResponse>> getAllPizzaBases() {
        List<PizzaBaseResponse> response = pizzaBaseService.getAllPizzaBases();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/texture/{texture}")
    @Operation(summary = "Get pizza bases by texture",
              description = "Retrieves all pizza bases with the specified texture")
    public ResponseEntity<List<PizzaBaseResponse>> getPizzaBasesByTexture(
            @PathVariable
            @Schema(description = "Texture of the pizza base",
                    allowableValues = {"THIN", "THICK", "STUFFED"},
                    example = "THIN")
            String texture) {
        List<PizzaBaseResponse> response = pizzaBaseService.getPizzaBasesByTexture(texture);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaBaseResponse> updatePizzaBase(
            @PathVariable String id,
            @Valid @RequestBody PizzaBaseRequest request) {
        PizzaBaseResponse response = pizzaBaseService.updatePizzaBase(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizzaBase(@PathVariable String id) {
        pizzaBaseService.deletePizzaBase(id);
        return ResponseEntity.noContent().build();
    }
}