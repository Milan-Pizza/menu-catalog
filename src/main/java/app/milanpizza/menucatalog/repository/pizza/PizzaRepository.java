package app.milanpizza.menucatalog.repository.pizza;

import app.milanpizza.menucatalog.domain.pizza.Pizza;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends MongoRepository<Pizza, String> {

    List<Pizza> findByMenuId(String menuId);

    List<Pizza> findByMenuIdAndIsAvailable(String menuId, boolean isAvailable);

    List<Pizza> findByCategory(String category);

    List<Pizza> findByIsPopular(boolean isPopular);

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Pizza> findByNameContainingIgnoreCase(String name);

    @Query("{'menuId': ?0, 'isAvailable': true, 'category': ?1}")
    List<Pizza> findAvailableByMenuAndCategory(String menuId, String category);
}