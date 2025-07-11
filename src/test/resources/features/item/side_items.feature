Feature: Side Item Management
  As a menu manager
  I want to manage side items
  So that customers can order accompaniments with their meals

  Background:
    Given a menu exists with:
      | regionCode | name       |
      | NY         | NY Special |
    And the menu ID is stored as "nyMenuId"

  Scenario: Add a new side item
    When I create a side item with:
      | menuId    | name        | price | category  |
      | ${nyMenuId} | Garlic Bread | 4.99  | BREAD     |
    Then the side item should be successfully created
    And the response should contain:
      | name        | price | category |
      | Garlic Bread | 4.99  | BREAD    |