Feature: Topping Management
  As a kitchen manager
  I want to manage pizza toppings
  So that we can offer diverse pizza customization

  Scenario: Create a new topping
    When I create a topping with:
      | name       | category  | baseCostPerUnit | isPremium |
      | Pepperoni  | MEAT      | 0.75            | false     |
    Then the topping should be successfully created
    And the response should contain:
      | name       | category  | baseCostPerUnit |
      | Pepperoni  | MEAT      | 0.75            |

  Scenario: Get toppings by category
    Given the following toppings exist:
      | name       | category  |
      | Pepperoni  | MEAT      |
      | Mushrooms  | VEGETABLE |
      | Mozzarella | CHEESE    |
    When I get toppings in category "CHEESE"
    Then the response should contain 1 topping
    And the response should contain topping "Mozzarella"