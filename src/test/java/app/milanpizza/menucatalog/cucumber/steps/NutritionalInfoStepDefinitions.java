package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.dto.response.metadata.NutritionalInfoResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class NutritionalInfoStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<NutritionalInfoResponse> response;

    @Given("nutritional info exists for item {string} of type {string}")
    public void nutritional_info_exists_for_item(String itemId, String itemType) {
        // Nutritional info creation would typically be done through a service
        // For testing, we'll assume it's created with the item
    }

    @When("I get nutritional info for item {string} of type {string}")
    public void i_get_nutritional_info_for_item(String itemId, String itemType) {
        response = restTemplate.getForEntity(
                getBaseUrl() + "/api/nutrition?itemId=" + itemId + "&itemType=" + itemType,
                NutritionalInfoResponse.class);
    }

    @Then("the nutritional info should be returned")
    public void the_nutritional_info_should_be_returned() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Then("it should contain calorie information")
    public void it_should_contain_calorie_information() {
        NutritionalInfoResponse info = response.getBody();
        assertNotNull(info.getCaloriesPerServing());
        assertTrue(info.getCaloriesPerServing() > 0);
    }
}