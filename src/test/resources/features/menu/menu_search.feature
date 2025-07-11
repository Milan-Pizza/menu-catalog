Feature: Menu Search and Filtering
  As a restaurant manager
  I want to search and filter menus
  So that I can find specific menus efficiently

  Background:
    Given the following menus exist:
      | regionCode | name          | isActive | validFrom   | validTo     |
      | NY         | NY Summer     | true     | 2023-06-01 | 2023-08-31 |
      | NY         | NY Winter     | true     | 2023-12-01 | 2024-02-28 |
      | CA         | California    | true     | 2023-01-01 | 2023-12-31 |
      | TX         | Texas Special | false    | 2023-01-01 | 2023-12-31 |

  @Search
  Scenario: Search menus by region code
    When I search menus with region code "NY"
    Then the response should contain 2 menus
    And the response should contain menu "NY Summer"
    And the response should contain menu "NY Winter"

  @Filter
  Scenario: Filter active menus
    When I filter active menus
    Then the response should contain 3 menus
    And the response should not contain menu "Texas Special"

  @DateFilter
  Scenario: Filter menus by date range
    When I filter menus valid between "2023-12-01" and "2024-01-31"
    Then the response should contain 1 menu
    And the response should contain menu "NY Winter"

  @CombinedSearch
  Scenario: Combined search and filter
    When I search active menus with region code "NY" valid in "2023-07-01"
    Then the response should contain 1 menu
    And the response should contain menu "NY Summer"