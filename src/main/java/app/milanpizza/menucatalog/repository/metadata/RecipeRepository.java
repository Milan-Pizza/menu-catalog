package app.milanpizza.menucatalog.repository.metadata;

import app.milanpizza.menucatalog.domain.metadata.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    Recipe findByPizzaId(String pizzaId);

    List<Recipe> findByDifficultyLevel(String difficultyLevel);
}