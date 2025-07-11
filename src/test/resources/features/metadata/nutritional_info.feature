Feature: Nutritional Information
  As a health-conscious customer
  I want to view nutritional information
  So that I can make informed meal choices

  Background:
    Given a pizza exists with:
      | name      | description       |
      | Veggie    | Vegetable pizza   |
    And the pizza ID is stored as "veggieId"

  Scenario: Get nutritional info for pizza
    Given nutritional info exists for item "${veggieId}" of type "PIZZA"
    When I get nutritional info for item "${veggieId}" of type "PIZZA"
    Then the nutritional info should be returned
    And it should contain calorie information