package app.milanpizza.menucatalog.service.metadata;

import app.milanpizza.menucatalog.dto.response.shared.NutritionalInfoResponse;

public interface NutritionalInfoService {
    NutritionalInfoResponse getNutritionalInfoByItem(String itemId, String itemType);
}