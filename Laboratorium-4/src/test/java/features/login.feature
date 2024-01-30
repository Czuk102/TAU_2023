Feature: Login functionality on saucedemo.com

  Scenario Outline: Standard user logs in successfully
    Given I open the browser "<browser>"
    Given I am on the saucedemo.com login page
    When I enter username "<username>" in the login field
    And I enter password "<password>" in the password field
    And I click the "Login" button
    Then the browser path should confirm a successful login

    Examples:
      | browser | username      | password     |
      | Chrome  | standard_user | secret_sauce |
      | Firefox | standard_user | secret_sauce |

