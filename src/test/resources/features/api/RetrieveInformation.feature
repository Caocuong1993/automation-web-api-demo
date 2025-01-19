Feature: API call api retrieve information from this a github project

  @api
  Scenario: Retrieve information from this a github project
    Given user call api get all repositories
    Then user verify API response code display equals 200
    And user get total open issues are there across all repositories
    And user get repository has the most watchers
    And user sort the repositories by date updated in descending order

