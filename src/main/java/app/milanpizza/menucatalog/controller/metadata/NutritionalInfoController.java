package app.milanpizza.menucatalog.controller.metadata;

import app.milanpizza.menucatalog.domain.enums.ItemType;
import app.milanpizza.menucatalog.dto.request.metadata.NutritionalInfoRequest;
import app.milanpizza.menucatalog.dto.response.metadata.NutritionalInfoResponse;
import app.milanpizza.menucatalog.service.metadata.NutritionalInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition")
@RequiredArgsConstructor
@Tag(name = "Nutritional Information Management", description = "Manage nutritional information for menu items")
public class NutritionalInfoController {

    private final NutritionalInfoService nutritionalInfoService;

    @PostMapping
    @Operation(summary = "Create nutritional information",
            description = "Create new nutritional information for a menu item")
    @ApiResponse(responseCode = "201", description = "Nutritional info created successfully",
            content = @Content(schema = @Schema(implementation = NutritionalInfoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Nutritional info already exists for this item")
    public ResponseEntity<NutritionalInfoResponse> createNutritionalInfo(
            @Valid @RequestBody NutritionalInfoRequest request) {
        NutritionalInfoResponse response = nutritionalInfoService.createNutritionalInfo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get nutritional information",
            description = "Retrieve nutritional information by item ID and type")
    @ApiResponse(responseCode = "200", description = "Nutritional info found",
            content = @Content(schema = @Schema(implementation = NutritionalInfoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Nutritional info not found")
    public ResponseEntity<NutritionalInfoResponse> getNutritionalInfo(
            @Parameter(description = "ID of the item (pizza, side, drink)", required = true)
            @RequestParam String itemId,
            @Parameter(description = "Type of the item (PIZZA, SIDE, DRINK)", required = true)
            @RequestParam ItemType itemType) {
        NutritionalInfoResponse response = nutritionalInfoService.getNutritionalInfoByItem(itemId, itemType);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update nutritional information",
            description = "Update existing nutritional information")
    @ApiResponse(responseCode = "200", description = "Nutritional info updated successfully",
            content = @Content(schema = @Schema(implementation = NutritionalInfoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Nutritional info not found")
    public ResponseEntity<NutritionalInfoResponse> updateNutritionalInfo(
            @Parameter(description = "ID of the nutritional info record", required = true)
            @PathVariable String id,
            @Valid @RequestBody NutritionalInfoRequest request) {
        NutritionalInfoResponse response = nutritionalInfoService.updateNutritionalInfo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete nutritional information",
            description = "Delete nutritional information by ID")
    @ApiResponse(responseCode = "204", description = "Nutritional info deleted successfully")
    @ApiResponse(responseCode = "404", description = "Nutritional info not found")
    public ResponseEntity<Void> deleteNutritionalInfo(
            @Parameter(description = "ID of the nutritional info record", required = true)
            @PathVariable String id) {
        nutritionalInfoService.deleteNutritionalInfo(id);
        return ResponseEntity.noContent().build();
    }
}