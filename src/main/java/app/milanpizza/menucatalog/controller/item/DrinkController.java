package app.milanpizza.menucatalog.controller.item;

import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.service.item.DrinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @PostMapping
    public ResponseEntity<DrinkResponse> createDrink(
            @Valid @RequestBody CreateDrinkRequest request) {
        DrinkResponse response = drinkService.createDrink(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrinkResponse> getDrinkById(@PathVariable String id) {
        DrinkResponse response = drinkService.getDrinkById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<DrinkResponse>> getDrinksByMenu(
            @PathVariable String menuId) {
        List<DrinkResponse> response = drinkService.getAllDrinksByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}/available")
    public ResponseEntity<List<DrinkResponse>> getAvailableDrinksByMenu(
            @PathVariable String menuId) {
        List<DrinkResponse> response = drinkService.getAvailableDrinksByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrinkResponse> updateDrink(
            @PathVariable String id,
            @Valid @RequestBody UpdateDrinkRequest request) {
        DrinkResponse response = drinkService.updateDrink(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<Void> toggleDrinkAvailability(@PathVariable String id) {
        drinkService.toggleDrinkAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable String id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }
}