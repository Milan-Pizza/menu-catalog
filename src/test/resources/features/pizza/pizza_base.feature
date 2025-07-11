Feature: Pizza Base Management
  As a kitchen manager
  I want to manage pizza base options
  So that we can offer different crust styles

  Scenario: Create a new pizza base
    When I create a pizza base with:
      | name        | texture | basePrice | isGlutenFree |
      | Thin Crust  | THIN    | 2.50      | false        |
    Then the pizza base should be successfully created
    And the response should contain:
      | name        | texture | basePrice |
      | Thin Crust  | THIN    | 2.50      |

  Scenario: Get all gluten-free bases
    Given the following pizza bases exist:
      | name        | texture | isGlutenFree |
      | Thin Crust  | THIN    | false        |
      | Gluten Free | THICK   | true         |
    When I get all gluten-free pizza bases
    Then the response should contain 1 base
    And the response should contain base "Gluten Free"