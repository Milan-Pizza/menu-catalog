package app.milanpizza.menucatalog.service.metadata;

import app.milanpizza.menucatalog.domain.enums.ItemType;
import app.milanpizza.menucatalog.dto.request.metadata.NutritionalInfoRequest;
import app.milanpizza.menucatalog.dto.response.metadata.NutritionalInfoResponse;

public interface NutritionalInfoService {
    NutritionalInfoResponse createNutritionalInfo(NutritionalInfoRequest request);
    NutritionalInfoResponse getNutritionalInfoByItem(String itemId, ItemType itemType);
    NutritionalInfoResponse updateNutritionalInfo(String id, NutritionalInfoRequest request);
    void deleteNutritionalInfo(String id);
}