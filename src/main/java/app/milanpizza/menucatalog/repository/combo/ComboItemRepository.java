package app.milanpizza.menucatalog.repository.combo;

import app.milanpizza.menucatalog.domain.combo.ComboItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboItemRepository extends MongoRepository<ComboItem, String> {

    List<ComboItem> findByComboId(String comboId);

    List<ComboItem> findByComboIdAndItemType(String comboId, String itemType);

    boolean existsByComboIdAndItemIdAndItemType(String comboId, String itemId, String itemType);
}