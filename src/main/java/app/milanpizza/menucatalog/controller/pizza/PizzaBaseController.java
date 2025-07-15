package app.milanpizza.menucatalog.controller.pizza;

import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import app.milanpizza.menucatalog.service.pizza.PizzaBaseService;
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
@RequestMapping(value = "/api/pizza-bases", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Pizza Base Management", description = "Endpoints for managing pizza bases")
public class PizzaBaseController {

    private final PizzaBaseService pizzaBaseService;

    @PostMapping
    @Operation(summary = "Create a new pizza base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pizza base created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Pizza base already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaBaseResponse> createPizzaBase(
            @Valid @RequestBody PizzaBaseRequest request) {
        PizzaBaseResponse response = pizzaBaseService.createPizzaBase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pizza base by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza base found"),
            @ApiResponse(responseCode = "404", description = "Pizza base not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaBaseResponse> getPizzaBaseById(
            @Parameter(description = "ID of pizza base to be retrieved", required = true)
            @PathVariable String id) {
        PizzaBaseResponse response = pizzaBaseService.getPizzaBaseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all pizza bases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza bases retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<PizzaBaseResponse>> getAllPizzaBases() {
        List<PizzaBaseResponse> response = pizzaBaseService.getAllPizzaBases();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/texture/{texture}")
    @Operation(summary = "Get pizza bases by texture",
            description = "Retrieves all pizza bases with the specified texture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza bases retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid texture value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<PizzaBaseResponse>> getPizzaBasesByTexture(
            @Parameter(description = "Texture of the pizza base", required = true,
                    schema = @Schema(description = "Texture type",
                            allowableValues = {"THIN", "THICK", "STUFFED"},
                            example = "THIN"))
            @PathVariable String texture) {
        List<PizzaBaseResponse> response = pizzaBaseService.getPizzaBasesByTexture(texture);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update pizza base by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza base updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Pizza base not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PizzaBaseResponse> updatePizzaBase(
            @Parameter(description = "ID of pizza base to be updated", required = true)
            @PathVariable String id,
            @Valid @RequestBody PizzaBaseRequest request) {
        PizzaBaseResponse response = pizzaBaseService.updatePizzaBase(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete pizza base by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pizza base deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza base not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deletePizzaBase(
            @Parameter(description = "ID of pizza base to be deleted", required = true)
            @PathVariable String id) {
        pizzaBaseService.deletePizzaBase(id);
        return ResponseEntity.noContent().build();
    }
}