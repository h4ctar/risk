Feature: Data Store

  Scenario: Write a new record
    When a write record message is received
    Then a record updated message is broadcasted

  Scenario: Delete an existing record
    Given there is an existing record
    When a delete record message is received
    Then a record deleted message is broadcasted

  Scenario: Request all records
    Given there is an existing record
    When a request all records message is received
    Then a record updated message is broadcasted

  Scenario: Delete all records
    Given there is an existing record
    When a delete all records message is received
    Then a record deleted message is broadcasted
