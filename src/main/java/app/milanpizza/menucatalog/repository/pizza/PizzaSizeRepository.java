package app.milanpizza.menucatalog.repository.pizza;

import app.milanpizza.menucatalog.domain.pizza.PizzaSize;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaSizeRepository extends MongoRepository<PizzaSize, String> {

    List<PizzaSize> findByIsPopular(boolean isPopular);

    List<PizzaSize> findByName(String name);
}