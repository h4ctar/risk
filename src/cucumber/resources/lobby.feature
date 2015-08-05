Feature: Lobby

  Scenario: Not enough players
    Given the system is waiting for players
    And less than 3 players have joined the game
    When all players are ready to play
    Then the system is waiting for players

  Scenario Outline: Start the game with more than 2 players
    Given the system is waiting for players
    And <players> players have joined the game
    When all players are ready to play
    And the system moves into the 'TRADING' state

    Examples:
      | players |
      |  3      |
      |  6      |

  Scenario: Deny access to the lobby once it's full
    Given the system is waiting for players
    And 6 players have joined the game
    When another player tries to join the game
    Then the system does not let them join