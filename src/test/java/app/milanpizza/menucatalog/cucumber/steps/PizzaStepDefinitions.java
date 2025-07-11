package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.domain.enums.PizzaCategory;
import app.milanpizza.menucatalog.dto.request.pizza.CreatePizzaRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaDetailedResponse;
import app.milanpizza.menucatalog.cucumber.steps.MenuStepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MenuStepDefinitions menuSteps;

    private ResponseEntity<PizzaDetailedResponse> response;
    private HttpStatus statusCode;

    @Given("a pizza exists with:")
    public void a_pizza_exists_with(Map<String, String> pizzaData) {
        menuSteps.a_menu_exists_with(Map.of(
                "regionCode", "TEMP",
                "name", "TEMP"
        ));

        String menuId = menuSteps.response.getBody().getMenuId();

        CreatePizzaRequest request = new CreatePizzaRequest();
        request.setMenuId(menuId);
        request.setName(pizzaData.get("name"));
        request.setIsAvailable(Boolean.parseBoolean(pizzaData.getOrDefault("isAvailable", "true")));

        if (pizzaData.containsKey("basePrice")) {
            request.setBasePrice(Double.parseDouble(pizzaData.get("basePrice")));
        }
        if (pizzaData.containsKey("category")) {
            request.setCategory(PizzaCategory.valueOf(pizzaData.get("category")));
        }

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/pizzas", request, PizzaDetailedResponse.class);
    }

    @When("I create a pizza with:")
    public void i_create_a_pizza_with(Map<String, String> pizzaData) {
        CreatePizzaRequest request = new CreatePizzaRequest();
        request.setMenuId(pizzaData.get("menuId"));
        request.setName(pizzaData.get("name"));
        request.setDescription(pizzaData.get("description"));
        request.setIsAvailable(Boolean.parseBoolean(pizzaData.get("isAvailable")));
        request.setBasePrice(Double.parseDouble(pizzaData.get("basePrice")));
        request.setCategory(PizzaCategory.valueOf(pizzaData.get("category")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/pizzas", request, PizzaDetailedResponse.class);
        statusCode = (HttpStatus) response.getStatusCode();
    }

    @Then("the pizza should be successfully created")
    public void the_pizza_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, statusCode);
        assertNotNull(response.getBody());
    }
}