package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.domain.enums.SideCategory;
import app.milanpizza.menucatalog.dto.request.item.CreateSideItemRequest;
import app.milanpizza.menucatalog.dto.request.item.UpdateSideItemRequest;
import app.milanpizza.menucatalog.dto.response.item.SideItemResponse;
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

public class SideItemStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MenuStepDefinitions menuSteps;

    private ResponseEntity<SideItemResponse> response;
    private ResponseEntity<SideItemResponse[]> listResponse;
    private HttpStatus statusCode;
    private HttpClientErrorException exception;

    @When("I create a side item with:")
    public void i_create_a_side_item_with(Map<String, String> sideItemData) {
        CreateSideItemRequest request = new CreateSideItemRequest();
        request.setMenuId(sideItemData.get("menuId"));
        request.setName(sideItemData.get("name"));
        request.setPrice(Double.parseDouble(sideItemData.get("price")));
        request.setCategory(SideCategory.valueOf(sideItemData.get("category")));
        request.setIsAvailable(true);

        try {
            response = TestUtil.postJson(restTemplate,
                    getBaseUrl() + "/api/side-items", request, SideItemResponse.class);
            statusCode = (HttpStatus) response.getStatusCode();
        } catch (HttpClientErrorException e) {
            exception = e;
            statusCode = (HttpStatus) e.getStatusCode();
        }
    }

    @Then("the side item should be successfully created")
    public void the_side_item_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, statusCode);
        assertNotNull(response.getBody());
    }

    @Then("the side item response should contain:")
    public void the_side_item_response_should_contain(Map<String, String> expected) {
        SideItemResponse sideItem = response.getBody();
        assertNotNull(sideItem);

        expected.forEach((key, value) -> {
            switch (key) {
                case "name":
                    assertEquals(value, sideItem.getName());
                    break;
                case "price":
                    assertEquals(Double.parseDouble(value), sideItem.getPrice());
                    break;
                case "category":
                    assertEquals(value, sideItem.getCategory());
                    break;
            }
        });
    }

    @Given("a side item exists with:")
    public void a_side_item_exists_with(Map<String, String> sideItemData) {
        menuSteps.a_menu_exists_with(Map.of(
                "regionCode", "TEMP",
                "name", "TEMP"
        ));

        String menuId = menuSteps.response.getBody().getMenuId();

        CreateSideItemRequest request = new CreateSideItemRequest();
        request.setMenuId(menuId);
        request.setName(sideItemData.get("name"));
        request.setPrice(Double.parseDouble(sideItemData.getOrDefault("price", "5.99")));
        request.setCategory(SideCategory.valueOf(sideItemData.getOrDefault("category", "APPETIZER")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/side-items", request, SideItemResponse.class);
    }

    @When("I update the side item with:")
    public void i_update_the_side_item_with(Map<String, String> updateData) {
        String sideItemId = response.getBody().getSideId();
        UpdateSideItemRequest request = new UpdateSideItemRequest();

        if (updateData.containsKey("name")) {
            request.setName(updateData.get("name"));
        }
        if (updateData.containsKey("price")) {
            request.setPrice(Double.parseDouble(updateData.get("price")));
        }

        restTemplate.put(
                getBaseUrl() + "/api/side-items/" + sideItemId, request);
    }

    @When("I get all side items")
    public void i_get_all_side_items() {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/side-items",
                SideItemResponse[].class);
    }

    @Then("the response should contain {int} side items")
    public void the_response_should_contain_side_items(int count) {
        assertEquals(count, listResponse.getBody().length);
    }
}