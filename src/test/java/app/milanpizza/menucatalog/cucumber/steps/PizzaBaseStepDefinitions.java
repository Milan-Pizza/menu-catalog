package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.domain.enums.PizzaBaseTexture;
import app.milanpizza.menucatalog.dto.request.config.PizzaBaseRequest;
import app.milanpizza.menucatalog.dto.response.pizza.PizzaBaseResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaBaseStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<PizzaBaseResponse> response;
    private ResponseEntity<PizzaBaseResponse[]> listResponse;

    @When("I create a pizza base with:")
    public void i_create_a_pizza_base_with(Map<String, String> baseData) {
        PizzaBaseRequest request = new PizzaBaseRequest();
        request.setName(baseData.get("name"));
        request.setTexture(PizzaBaseTexture.valueOf(baseData.get("texture")));
        request.setBasePrice(Double.parseDouble(baseData.get("basePrice")));
        request.setIsGlutenFree(Boolean.parseBoolean(baseData.get("isGlutenFree")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/pizza-bases", request, PizzaBaseResponse.class);
    }

    @Then("the pizza base should be successfully created")
    public void the_pizza_base_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Given("the following pizza bases exist:")
    public void the_following_pizza_bases_exist(List<Map<String, String>> bases) {
        bases.forEach(base -> {
            PizzaBaseRequest request = new PizzaBaseRequest();
            request.setName(base.get("name"));
            request.setTexture(PizzaBaseTexture.valueOf(base.get("texture")));
            request.setIsGlutenFree(Boolean.parseBoolean(base.get("isGlutenFree")));
            restTemplate.postForEntity(
                    getBaseUrl() + "/api/pizza-bases", request, PizzaBaseResponse.class);
        });
    }

    @When("I get all gluten-free pizza bases")
    public void i_get_all_gluten_free_pizza_bases() {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/pizza-bases/texture/GLUTEN_FREE",
                PizzaBaseResponse[].class);
    }
}