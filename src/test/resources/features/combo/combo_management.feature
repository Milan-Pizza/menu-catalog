Feature: Combo Meal Management
  As a promotions manager
  I want to manage combo meals
  So that we can offer discounted meal packages

  Background:
    Given a menu exists with:
      | regionCode | name       |
      | NY         | NY Special |
    And the menu ID is stored as "nyMenuId"

  Scenario: Create a new combo meal
    When I create a combo with:
      | menuId    | name          | comboPrice | isActive |
      | ${nyMenuId} | Pizza + Drink | 15.99      | true     |
    Then the combo should be successfully created
    And the response should contain:
      | name          | comboPrice | isActive |
      | Pizza + Drink | 15.99      | true     |