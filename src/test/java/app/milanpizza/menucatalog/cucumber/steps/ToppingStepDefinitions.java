package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.domain.enums.ToppingCategory;
import app.milanpizza.menucatalog.dto.request.config.ToppingRequest;
import app.milanpizza.menucatalog.dto.response.pizza.AllowedToppingResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ToppingStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<AllowedToppingResponse> response;
    private ResponseEntity<AllowedToppingResponse[]> listResponse;

    @When("I create a topping with:")
    public void i_create_a_topping_with(Map<String, String> toppingData) {
        ToppingRequest request = new ToppingRequest();
        request.setName(toppingData.get("name"));
        request.setCategory(ToppingCategory.valueOf(toppingData.get("category")));
        request.setBaseCostPerUnit(Double.parseDouble(toppingData.get("baseCostPerUnit")));
        request.setIsPremium(Boolean.parseBoolean(toppingData.get("isPremium")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/toppings", request, AllowedToppingResponse.class);
    }

    @Then("the topping should be successfully created")
    public void the_topping_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Given("the following toppings exist:")
    public void the_following_toppings_exist(List<Map<String, String>> toppings) {
        toppings.forEach(topping -> {
            ToppingRequest request = new ToppingRequest();
            request.setName(topping.get("name"));
            request.setCategory(ToppingCategory.valueOf(topping.get("category")));
            restTemplate.postForEntity(
                    getBaseUrl() + "/api/toppings", request, AllowedToppingResponse.class);
        });
    }

    @When("I get toppings in category {string}")
    public void i_get_toppings_in_category(String category) {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/toppings/category/" + category,
                AllowedToppingResponse[].class);
    }
}