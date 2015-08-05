Feature: Fortifying

  Scenario: Fortifying
    Given the system is in the 'FORTIFYING' state

    Then the system sets the current player to the next player
    And the system moves to the 'TRADING' state