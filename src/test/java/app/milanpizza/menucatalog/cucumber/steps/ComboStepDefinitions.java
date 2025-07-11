package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.dto.request.combo.CreateComboRequest;
import app.milanpizza.menucatalog.dto.response.combo.ComboDetailedResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ComboStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MenuStepDefinitions menuSteps;

    private ResponseEntity<ComboDetailedResponse> response;

    @When("I create a combo with:")
    public void i_create_a_combo_with(Map<String, String> comboData) {
        CreateComboRequest request = new CreateComboRequest();
        request.setMenuId(comboData.get("menuId"));
        request.setName(comboData.get("name"));
        request.setComboPrice(Double.parseDouble(comboData.get("comboPrice")));
        request.setIsActive(Boolean.parseBoolean(comboData.getOrDefault("isActive", "true")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/combos", request, ComboDetailedResponse.class);
    }

    @Then("the combo should be successfully created")
    public void the_combo_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Then("the response should contain:")
    public void the_combo_response_should_contain(Map<String, String> expected) {
        ComboDetailedResponse combo = response.getBody();
        assertNotNull(combo);

        expected.forEach((key, value) -> {
            switch (key) {
                case "name":
                    assertEquals(value, combo.getName());
                    break;
                case "comboPrice":
                    assertEquals(Double.parseDouble(value), combo.getComboPrice());
                    break;
                case "isActive":
                    assertEquals(Boolean.parseBoolean(value), combo.getIsActive());
                    break;
            }
        });
    }
}