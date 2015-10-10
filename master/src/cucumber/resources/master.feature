Feature: Master

  Scenario: Start Lobby
    When Master starts
    Then the game state is set to LOBBY

  Scenario: Player joins Lobby
    When a join lobby message is received
    Then the player record is added

  Scenario: One Player is ready
    Given a join lobby message has been received
    When a player ready message is received
    Then the player record is set to ready

  Scenario: All Players are ready
    Given 3 players have joined the lobby
    When 3 player ready messages have been received
    Then the game state is set to TRADING
