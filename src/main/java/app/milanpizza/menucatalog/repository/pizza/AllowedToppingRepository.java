package app.milanpizza.menucatalog.repository.pizza;

import app.milanpizza.menucatalog.domain.pizza.AllowedTopping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllowedToppingRepository extends MongoRepository<AllowedTopping, String> {

    List<AllowedTopping> findByPizzaId(String pizzaId);

    List<AllowedTopping> findByPizzaIdAndIsDefault(String pizzaId, boolean isDefault);

    List<AllowedTopping> findByPizzaIdAndIsRecommended(String pizzaId, boolean isRecommended);

    boolean existsByPizzaIdAndToppingId(String pizzaId, String toppingId);

    void deleteByPizzaIdAndToppingId(String pizzaId, String toppingId);
}