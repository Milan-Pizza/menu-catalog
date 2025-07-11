package app.milanpizza.menucatalog.repository.combo;

import app.milanpizza.menucatalog.domain.combo.ComboMeal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComboMealRepository extends MongoRepository<ComboMeal, String> {

    List<ComboMeal> findByMenuId(String menuId);

    List<ComboMeal> findByIsActive(boolean isActive);

    @Query("{'validFrom': {$lte: ?0}, 'validTo': {$gte: ?0}, 'isActive': true}")
    List<ComboMeal> findActiveCombosByDate(LocalDateTime date);

    @Query("{'menuId': ?0, 'isActive': true}")
    List<ComboMeal> findActiveCombosByMenu(String menuId);

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<ComboMeal> findByNameContainingIgnoreCase(String name);
}