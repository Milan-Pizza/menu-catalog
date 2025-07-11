package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.domain.enums.DrinkCategory;
import app.milanpizza.menucatalog.domain.enums.DrinkSize;
import app.milanpizza.menucatalog.dto.request.item.CreateDrinkRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateDrinkRequest;
import app.milanpizza.menucatalog.dto.response.item.DrinkResponse;
import app.milanpizza.menucatalog.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DrinkStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MenuStepDefinitions menuSteps;

    private ResponseEntity<DrinkResponse> response;
    private ResponseEntity<DrinkResponse[]> listResponse;
    private HttpStatus statusCode;
    private HttpClientErrorException exception;

    @When("I create a drink with:")
    public void i_create_a_drink_with(Map<String, String> drinkData) {
        CreateDrinkRequest request = new CreateDrinkRequest();
        request.setMenuId(drinkData.get("menuId"));
        request.setName(drinkData.get("name"));
        request.setCategory(DrinkCategory.valueOf(drinkData.get("category")));
        request.setSize(DrinkSize.valueOf(drinkData.get("size")));
        request.setPrice(Double.parseDouble(drinkData.get("price")));
        request.setIsAvailable(true);

        try {
            response = TestUtil.postJson(restTemplate,
                    getBaseUrl() + "/api/drinks", request, DrinkResponse.class);
            statusCode = (HttpStatus) response.getStatusCode();
        } catch (HttpClientErrorException e) {
            exception = e;
            statusCode = (HttpStatus) e.getStatusCode();
        }
    }

    @Then("the drink should be successfully created")
    public void the_drink_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, statusCode);
        assertNotNull(response.getBody());
    }

    @Then("the drink response should contain:")
    public void the_drink_response_should_contain(Map<String, String> expected) {
        DrinkResponse drink = response.getBody();
        assertNotNull(drink);

        expected.forEach((key, value) -> {
            switch (key) {
                case "name":
                    assertEquals(value, drink.getName());
                    break;
                case "category":
                    assertEquals(value, drink.getCategory());
                    break;
                case "size":
                    assertEquals(value, drink.getSize());
                    break;
                case "price":
                    assertEquals(Double.parseDouble(value), drink.getPrice());
                    break;
            }
        });
    }

    @Given("a drink exists with:")
    public void a_drink_exists_with(Map<String, String> drinkData) {
        menuSteps.a_menu_exists_with(Map.of(
                "regionCode", "TEMP",
                "name", "TEMP"
        ));

        String menuId = menuSteps.response.getBody().getMenuId();

        CreateDrinkRequest request = new CreateDrinkRequest();
        request.setMenuId(menuId);
        request.setName(drinkData.get("name"));
        request.setCategory(DrinkCategory.valueOf(drinkData.getOrDefault("category", "SOFT_DRINK")));
        request.setSize(DrinkSize.valueOf(drinkData.getOrDefault("size", "MEDIUM")));
        request.setPrice(Double.parseDouble(drinkData.getOrDefault("price", "2.99")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/drinks", request, DrinkResponse.class);
    }

    @When("I get drinks by category {string}")
    public void i_get_drinks_by_category(String category) {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/drinks?category=" + category,
                DrinkResponse[].class);
    }

    @When("I toggle availability for drink {string}")
    public void i_toggle_availability_for_drink(String drinkId) {
        restTemplate.patchForObject(
                getBaseUrl() + "/api/drinks/" + drinkId + "/toggle-availability",
                null, Void.class);
    }

    @Then("the drink should have isAvailable {string}")
    public void the_drink_should_have_isAvailable(String expected) {
        DrinkResponse drink = restTemplate.getForObject(
                getBaseUrl() + "/api/drinks/" + response.getBody().getDrinkId(),
                DrinkResponse.class);
        assertEquals(Boolean.parseBoolean(expected), drink.getIsAvailable());
    }
}