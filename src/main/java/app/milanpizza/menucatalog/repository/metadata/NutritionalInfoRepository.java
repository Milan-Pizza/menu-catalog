package app.milanpizza.menucatalog.repository.metadata;

import app.milanpizza.menucatalog.domain.metadata.NutritionalInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionalInfoRepository extends MongoRepository<NutritionalInfo, String> {

    NutritionalInfo findByItemIdAndItemType(String itemId, String itemType);

    List<NutritionalInfo> findByItemType(String itemType);

    @Query("{'caloriesPerServing': {$lte: ?0}}")
    List<NutritionalInfo> findByMaxCalories(double maxCalories);
}