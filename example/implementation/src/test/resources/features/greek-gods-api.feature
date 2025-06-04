Feature: Greek Gods API Data Retrieval
  As a developer
  I want to retrieve Greek god names via REST API
  So that I can access a complete dataset for my application

  Background:
    Given the Greek Gods API is running
    And the database contains 20 Greek god names

  Scenario: Successfully retrieve complete list of Greek god names
    When I send a GET request to "/api/v1/gods/greek"
    Then I should receive a 200 OK response
    And the response should be a JSON array
    And the response should contain exactly 20 god names
    And the response should include "Zeus", "Hera", "Poseidon"
    And the response time should be less than 1 second

  Scenario: Handle database connection failure gracefully
    Given the database is unavailable
    When I send a GET request to "/api/v1/gods/greek"
    Then I should receive a 500 Internal Server Error response
    And the response should be in JSON format
    And the response should contain an error message

  Scenario: Handle empty database scenario
    Given the database is empty
    When I send a GET request to "/api/v1/gods/greek"
    Then I should receive a 200 OK response
    And the response should be an empty JSON array
