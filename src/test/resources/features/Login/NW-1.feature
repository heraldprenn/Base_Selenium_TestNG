@Regression
Feature: NW-1: Login-Logout

  Scenario Outline: 1: Successful Login
    Given the user is on the login page
    When the user logs in with the credentials <username> and <password>
    Then the user <user> will be logged in successfully

    Examples: 
      | username  | password  | user           |
      | northwind | enterlang | Northwind User |

  Scenario: 2: Logout
    Given the user is logged in
    When the user logs out
    Then the user will be back on the login page

  Scenario Outline: 3: Invalid Login
    Given the user is on the login page
    When the user logs in with the credentials <username> and <password>
    Then the feedback message "Invalid username or password." will be displayed

    Examples: 
      | username  | password      |
      | northwind | wrongPassword |
