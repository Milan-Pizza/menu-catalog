package app.milanpizza.menucatalog.controller.combo;

import app.milanpizza.menucatalog.dto.request.combo.CreateComboRequest;
import app.milanpizza.menucatalog.dto.request.combo.UpdateComboRequest;
import app.milanpizza.menucatalog.dto.response.combo.ComboDetailedResponse;
import app.milanpizza.menucatalog.dto.response.combo.ComboSummaryResponse;
import app.milanpizza.menucatalog.service.combo.ComboService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combos")
@RequiredArgsConstructor
public class ComboController {

    private final ComboService comboService;

    @PostMapping
    public ResponseEntity<ComboDetailedResponse> createCombo(
            @Valid @RequestBody CreateComboRequest request) {
        ComboDetailedResponse response = comboService.createCombo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboDetailedResponse> getComboById(@PathVariable String id) {
        ComboDetailedResponse response = comboService.getComboById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<ComboSummaryResponse>> getCombosByMenu(
            @PathVariable String menuId) {
        List<ComboSummaryResponse> response = comboService.getAllCombosByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menu/{menuId}/active")
    public ResponseEntity<List<ComboSummaryResponse>> getActiveCombosByMenu(
            @PathVariable String menuId) {
        List<ComboSummaryResponse> response = comboService.getActiveCombosByMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComboDetailedResponse> updateCombo(
            @PathVariable String id,
            @Valid @RequestBody UpdateComboRequest request) {
        ComboDetailedResponse response = comboService.updateCombo(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<Void> toggleComboAvailability(@PathVariable String id) {
        comboService.toggleComboAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable String id) {
        comboService.deleteCombo(id);
        return ResponseEntity.noContent().build();
    }
}