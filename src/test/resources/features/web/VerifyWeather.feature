Feature: WEB App verify weather

  @web
  Scenario Outline: Verify weather in Los Angeles US
    Given open web app
    When user input location want to search: "<location>"
    And user click search button
    And user choose item: "<location>"
    Then user verify the city name: "<location>" is displayed correctly
    Then user verify the current date is displayed correctly
    Then user verify the temperature is displayed correctly and it is a number
    Examples:
      | location        |
      | Los Angeles, US |