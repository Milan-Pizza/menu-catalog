package app.milanpizza.menucatalog.repository.item;

import app.milanpizza.menucatalog.domain.item.Drink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends MongoRepository<Drink, String> {

    List<Drink> findByMenuId(String menuId);

    List<Drink> findByMenuIdAndIsAvailable(String menuId, boolean isAvailable);

    List<Drink> findByCategory(String category);

    List<Drink> findBySize(String size);

    List<Drink> findByIsCold(boolean isCold);
}