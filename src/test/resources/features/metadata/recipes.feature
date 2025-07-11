Feature: Recipe Management
  As a kitchen staff member
  I want to access pizza recipes
  So that I can prepare pizzas correctly

  Background:
    Given a pizza exists with:
      | name      | description       |
      | Margherita | Classic margherita |
    And the pizza ID is stored as "margheritaId"

  Scenario: Get recipe for pizza
    Given a recipe exists for pizza "${margheritaId}"
    When I get the recipe for pizza "${margheritaId}"
    Then the recipe should be returned
    And the recipe should contain preparation steps