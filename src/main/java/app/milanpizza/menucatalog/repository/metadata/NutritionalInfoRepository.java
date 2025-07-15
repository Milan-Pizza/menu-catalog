package app.milanpizza.menucatalog.repository.metadata;

import app.milanpizza.menucatalog.domain.metadata.NutritionalInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutritionalInfoRepository extends MongoRepository<NutritionalInfo, String> {

    Optional<NutritionalInfo> findByItemIdAndItemType(String itemId, String itemType);

    List<NutritionalInfo> findByItemType(String itemType);

    @Query("{'caloriesPerServing': {$lte: ?0}}")
    List<NutritionalInfo> findByMaxCalories(double maxCalories);

    boolean existsByItemIdAndItemType(String itemId, String itemType);

    Optional<NutritionalInfo> findByItemId(String id);
}