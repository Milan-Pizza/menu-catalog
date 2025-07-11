Feature: Pizza Management
  As a menu manager
  I want to manage pizza offerings
  So that customers can order from our pizza selection

  Background:
    Given a menu exists with:
      | regionCode | name       |
      | NY         | NY Special |
    And the menu ID is stored as "nyMenuId"

  Scenario: Add a new pizza to the menu
    When I create a pizza with:
      | menuId    | name          | description       | isAvailable | basePrice | category |
      | ${nyMenuId} | Pepperoni     | Classic pepperoni | true        | 12.99     | CLASSIC  |
    Then the pizza should be successfully created
    And the response should contain:
      | name      | isAvailable | basePrice |
      | Pepperoni | true        | 12.99     |

  Scenario: Update pizza availability
    Given a pizza exists with:
      | menuId    | name      | isAvailable |
      | ${nyMenuId} | Margherita | true       |
    And the pizza ID is stored as "margheritaId"
    When I toggle availability for pizza "${margheritaId}"
    Then the pizza should have isAvailable "false"