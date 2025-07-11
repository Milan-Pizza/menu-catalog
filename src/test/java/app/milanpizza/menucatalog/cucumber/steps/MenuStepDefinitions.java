package app.milanpizza.menucatalog.cucumber.steps;

import app.milanpizza.menucatalog.config.TestConfig;
import app.milanpizza.menucatalog.dto.request.menu.CreateMenuRequest;
import app.milanpizza.menucatalog.dto.response.menu.MenuDetailedResponse;
import app.milanpizza.menucatalog.dto.response.menu.MenuSummaryResponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuStepDefinitions extends TestConfig {

    @Autowired
    private RestTemplate restTemplate;

    ResponseEntity<MenuDetailedResponse> response;
    private ResponseEntity<MenuSummaryResponse[]> listResponse;
    private HttpStatus statusCode;
    private HttpClientErrorException exception;

    @Before
    public void setup() {
        restTemplate.delete(getBaseUrl() + "/api/menus");
    }

    @Given("the system has no menus")
    public void the_system_has_no_menus() {
        ResponseEntity<MenuSummaryResponse[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/api/menus", MenuSummaryResponse[].class);
        assertEquals(0, Objects.requireNonNull(response.getBody()).length);
    }

    @Given("a menu exists with:")
    public void a_menu_exists_with(Map<String, String> menuData) {
        CreateMenuRequest request = new CreateMenuRequest();
        request.setRegionCode(menuData.get("regionCode"));
        request.setName(menuData.get("name"));
        request.setValidFrom(LocalDate.parse(menuData.getOrDefault("validFrom", "2023-01-01")));
        request.setValidTo(LocalDate.parse(menuData.getOrDefault("validTo", "2023-12-31")));
        request.setIsActive(Boolean.parseBoolean(menuData.getOrDefault("isActive", "true")));

        response = restTemplate.postForEntity(
                getBaseUrl() + "/api/menus", request, MenuDetailedResponse.class);
    }

    @When("I create a menu with:")
    public void i_create_a_menu_with(Map<String, String> menuData) {
        CreateMenuRequest request = new CreateMenuRequest();
        request.setRegionCode(menuData.get("regionCode"));
        request.setName(menuData.get("name"));

        if (menuData.containsKey("validFrom")) {
            request.setValidFrom(LocalDate.parse(menuData.get("validFrom")));
        }
        if (menuData.containsKey("validTo")) {
            request.setValidTo(LocalDate.parse(menuData.get("validTo")));
        }
        if (menuData.containsKey("isActive")) {
            request.setIsActive(Boolean.parseBoolean(menuData.get("isActive")));
        }

        try {
            response = restTemplate.postForEntity(
                    getBaseUrl() + "/api/menus", request, MenuDetailedResponse.class);
            statusCode = (HttpStatus) response.getStatusCode();
        } catch (HttpClientErrorException e) {
            exception = e;
            statusCode = (HttpStatus) e.getStatusCode();
        }
    }

    @Then("the menu should be successfully created")
    public void the_menu_should_be_successfully_created() {
        assertEquals(HttpStatus.CREATED, statusCode);
        assertNotNull(response.getBody());
    }

    @Then("the response should contain:")
    public void the_response_should_contain(Map<String, String> expected) {
        MenuDetailedResponse menu = response.getBody();
        assertNotNull(menu);

        expected.forEach((key, value) -> {
            switch (key) {
                case "regionCode":
                    assertEquals(value, menu.getRegionCode());
                    break;
                case "name":
                    assertEquals(value, menu.getName());
                    break;
                case "isActive":
                    assertEquals(Boolean.parseBoolean(value), menu.getIsActive());
                    break;
            }
        });
    }

    // Add these methods to MenuStepDefinitions.java

    @When("I search menus with region code {string}")
    public void i_search_menus_with_region_code(String regionCode) {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/menus/region/" + regionCode,
                MenuSummaryResponse[].class);
    }

    @When("I filter active menus")
    public void i_filter_active_menus() {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/menus?isActive=true",
                MenuSummaryResponse[].class);
    }

    @When("I filter menus valid between {string} and {string}")
    public void i_filter_menus_valid_between(String from, String to) {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/menus/date-range?startDate=" + from + "&endDate=" + to,
                MenuSummaryResponse[].class);
    }

    @When("I search active menus with region code {string} valid in {string}")
    public void i_search_active_menus_with_region_code_valid_in(String regionCode, String date) {
        listResponse = restTemplate.getForEntity(
                getBaseUrl() + "/api/menus/search?region=" + regionCode + "&date=" + date + "&active=true",
                MenuSummaryResponse[].class);
    }

    @Then("the response should contain {int} menus")
    public void the_response_should_contain_menus(int count) {
        assertEquals(count, listResponse.getBody().length);
    }

    @Then("the response should contain menu {string}")
    public void the_response_should_contain_menu(String name) {
        assertTrue(Arrays.stream(listResponse.getBody())
                .anyMatch(menu -> menu.getName().equals(name)));
    }

    @Then("the response should not contain menu {string}")
    public void the_response_should_not_contain_menu(String name) {
        assertFalse(Arrays.stream(listResponse.getBody())
                .anyMatch(menu -> menu.getName().equals(name)));
    }
}