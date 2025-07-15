package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.dto.response.metadata.RecipeResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PizzaStepDefinitions pizzaSteps;

    private ResponseEntity<RecipeResponse> response;

    @Given("a recipe exists for pizza {string}")
    public void a_recipe_exists_for_pizza(String pizzaId) {
        // Recipe creation would typically be done through a service in a real application
        // For testing, we'll assume it's created when the pizza is created
    }

    @When("I get the recipe for pizza {string}")
    public void i_get_the_recipe_for_pizza(String pizzaId) {
        response = restTemplate.getForEntity(
                getBaseUrl() + "/api/recipes/pizza/" + pizzaId, RecipeResponse.class);
    }

    @Then("the recipe should be returned")
    public void the_recipe_should_be_returned() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Then("the recipe should contain preparation steps")
    public void the_recipe_should_contain_preparation_steps() {
        RecipeResponse recipe = response.getBody();
        assertNotNull(recipe.getPreparationSteps());
        assertFalse(recipe.getPreparationSteps().isEmpty());
    }
}