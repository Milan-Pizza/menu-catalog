package app.milanpizza.menucatalog.controller.metadata;

import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;
import app.milanpizza.menucatalog.service.metadata.NutritionalInfoService;
import app.milanpizza.menucatalog.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition")
@RequiredArgsConstructor
public class NutritionalInfoController extends BaseController {

    private final NutritionalInfoService nutritionalInfoService;

    @GetMapping
    public ResponseEntity<NutritionalInfoResponse> getNutritionalInfo(
            @RequestParam String itemId,
            @RequestParam String itemType) {
        NutritionalInfoResponse response = nutritionalInfoService.getNutritionalInfoByItem(itemId, itemType);
        return ResponseEntity.ok(response);
    }
}