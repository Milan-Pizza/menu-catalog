Feature: Menu Management
  As a restaurant manager
  I want to manage menus
  So that I can organize our offerings by region and time period

  Background:
    Given the system has no menus

  @HappyPath
  Scenario: Create a new menu
    When I create a menu with:
      | regionCode | name       | validFrom   | validTo     | isActive |
      | NY         | NY Special | 2023-01-01 | 2023-12-31 | true     |
    Then the menu should be successfully created
    And the response should contain:
      | regionCode | name       | isActive |
      | NY         | NY Special | true     |

  @Validation
  Scenario: Fail to create menu with duplicate region and name
    Given a menu exists with:
      | regionCode | name       | validFrom   | validTo     |
      | NY         | NY Special | 2023-01-01 | 2023-12-31 |
    When I create a menu with:
      | regionCode | name       |
      | NY         | NY Special |
    Then the response should indicate a conflict error

  @Search
  Scenario: Get active menus by region
    Given the following menus exist:
      | regionCode | name       | isActive | validFrom   | validTo     |
      | NY         | NY Special | true     | 2023-01-01 | 2023-12-31 |
      | NY         | NY Winter  | false    | 2023-11-01 | 2024-02-28 |
      | CA         | CA Special | true     | 2023-01-01 | 2023-12-31 |
    When I get active menus for region "NY"
    Then the response should contain 1 menu
    And the response should contain menu "NY Special"