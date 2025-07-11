package app.milanpizza.menucatalog.repository.pizza;

import app.milanpizza.menucatalog.domain.pizza.PizzaBase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaBaseRepository extends MongoRepository<PizzaBase, String> {

    List<PizzaBase> findByIsGlutenFree(boolean isGlutenFree);

    List<PizzaBase> findByIsVegan(boolean isVegan);

    List<PizzaBase> findByTexture(String texture);
}