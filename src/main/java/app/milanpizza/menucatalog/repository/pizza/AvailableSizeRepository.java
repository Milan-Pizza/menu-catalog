package app.milanpizza.menucatalog.repository.pizza;

import app.milanpizza.menucatalog.domain.pizza.AvailableSize;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableSizeRepository extends MongoRepository<AvailableSize, String> {

    List<AvailableSize> findByPizzaId(String pizzaId);

    List<AvailableSize> findByPizzaIdAndIsAvailable(String pizzaId, boolean isAvailable);

    List<AvailableSize> findByPizzaIdAndIsPopular(String pizzaId, boolean isPopular);

    boolean existsByPizzaIdAndSizeId(String pizzaId, String sizeId);
}