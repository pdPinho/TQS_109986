Feature: Login in practice site

  Scenario: Buy Flight
    When I navigate to "https://blazedemo.com/"
      And I choose "Boston" as my origin and "London" as my destination
      And I click Find Flights
    Then I should be redirected to "https://blazedemo.com/reserve.php"
      And I click Choose This Flight
    Then I should be redirected to "https://blazedemo.com/purchase.php"
      And I click Purchase Flight
    Then I should be redirected to "https://blazedemo.com/confirmation.php"
      And be presented with the title "BlazeDemo Confirmation"