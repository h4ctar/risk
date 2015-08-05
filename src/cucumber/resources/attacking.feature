Feature: Attacking

  Scenario: Loosing a battle
    Given the system is in the 'ATTACKING' state
    Then the system moves to the 'FORTIFYING' state

  Scenario: Winning a battle
    Given the system is in the 'ATTACKING' state
    Then the system gives the current player a card
    And the system moves to the 'FORTIFYING' state

  Scenario: Winning the game
    Given the system is in the 'ATTACKING' state
    When all enemies are dead
    Then the system moves to the 'GAME_OVER' state