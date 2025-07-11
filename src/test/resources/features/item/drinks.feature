Feature: Drink Management
  As a menu manager
  I want to manage drink offerings
  So that customers can order beverages with their meals

  Background:
    Given a menu exists with:
      | regionCode | name       |
      | NY         | NY Special |
    And the menu ID is stored as "nyMenuId"

  Scenario: Add a new drink
    When I create a drink with:
      | menuId    | name      | category    | size  | price |
      | ${nyMenuId} | Cola      | SOFT_DRINK  | LARGE | 2.99  |
    Then the drink should be successfully created
    And the response should contain:
      | name      | category    | size  | price |
      | Cola      | SOFT_DRINK  | LARGE | 2.99  |