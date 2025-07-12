package app.milanpizza.menucatalog.repository.pizza;

import app.milanpizza.menucatalog.domain.pizza.Topping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToppingRepository extends MongoRepository<Topping, String> {

    List<Topping> findByCategory(String category);

    List<Topping> findByIsVegetarian(boolean isVegetarian);

    List<Topping> findByIsVegan(boolean isVegan);

    List<Topping> findByIsPremium(boolean isPremium);

    boolean existsByName(String name);

}