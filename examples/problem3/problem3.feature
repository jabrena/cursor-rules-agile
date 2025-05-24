# Implementation & Testing Context:
# The API gateway is intended to be built using Spring Boot (e.g., 3.x) with Java (e.g., 21+).
# The tests for these scenarios are expected to be implemented using RestAssured against the REST HTTP interface.

Feature: God Mythology API Concurrent Access and Thread Safety
  This feature ensures that the God Mythology API can handle concurrent requests
  for different mythologies (GREEK, ROMAN, NORDIC, INDIAN, CELTIBERIAN) and maintain data integrity (thread safety).

  Background:
    Given the God Mythology API is configured
    And the API provides information for GREEK, ROMAN, NORDIC, INDIAN, CELTIBERIAN mythologies

  @NFR @Concurrency @ThreadSafety
  Scenario Outline: Concurrent retrieval of god information for a specific mythology
    When a simulated group of users concurrently request information for "<Mythology>" gods
    Then all users receive an HTTP 200 OK success response
    And each response body contains the pre-defined, complete list of "<Mythology>" gods
    And all captured responses for "<Mythology>" gods are identical, ensuring consistency

    Examples:
      | Mythology |
      | GREEK     |
      | ROMAN     |
      | NORDIC    |
      | INDIAN    |
      | CELTIBERIAN    |

  @NFR @Concurrency @ThreadSafety
  Scenario: Mixed concurrent requests ensure overall system thread safety
    When a simulated group of users concurrently request information for GREEK, ROMAN, NORDIC, INDIAN, CELTIBERIAN gods simultaneously over a defined period
    Then all requests for "GREEK" gods receive an HTTP 200 OK response with the correct list of Greek gods
    And all responses for "GREEK" gods are identical
    And all requests for "ROMAN" gods receive an HTTP 200 OK response with the correct list of Roman gods
    And all responses for "ROMAN" gods are identical
    And all requests for "NORDIC" gods receive an HTTP 200 OK response with the correct list of Nordic gods
    And all responses for "NORDIC" gods are identical
    And all requests for "INDIAN" gods receive an HTTP 200 OK response with the correct list of Indian gods
    And all responses for "INDIAN" gods are identical
    And all requests for "CELTIBERIAN" gods receive an HTTP 200 OK response with the correct list of Celtiberian gods
    And all responses for "CELTIBERIAN" gods are identical
    And no data corruption or thread-related errors are observed in the system during or after the concurrent operations
