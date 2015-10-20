Feature: Lobby

  Scenario Outline: Less than 3 players
    Given the system has started
    And <players> players have joined the game
    When all players are ready to play
    And the system moves into the LOBBY state

    Examples:
      | players |
      |  1      |
      |  2      |

  Scenario Outline: Start the game with more than 2 players
    Given the system has started
    And <players> players have joined the game
    When all players are ready to play
    And the system moves into the TRADING state

    Examples:
      | players |
      |  3      |
      |  6      |

  Scenario: Deny access to the lobby once it's full
    Given the system is waiting for players
    And 6 players have joined the game
    When another player tries to join the game
    Then the system does not let them join