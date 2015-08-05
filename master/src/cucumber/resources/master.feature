Feature: Master

  Scenario: Start Lobby
    When Master starts
    Then a WRITE_GAME_STATE message is sent to DATA_STORE with STATE = LOBBY
