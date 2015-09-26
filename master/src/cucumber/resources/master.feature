Feature: Master

  Scenario: Start Lobby
    When Master starts
    Then a ben.risk.irs.record.WriteRecord message is sent to DATA_STORE with:
      | record.type      | ben.risk.irs.game.GameRecord |
      | record.id        | 0                            |
      | record.gameState | LOBBY                        |

  Scenario: Player joins Lobby
    When a ben.risk.irs.JoinLobby message is received with:
      | playerName | Ben |

    Then a ben.risk.irs.record.WriteRecord message is sent to DATA_STORE with:
      | record.type       | ben.risk.irs.player.PlayerRecord |
      | record.id         | 0                                |
      | record.playerName | Ben                              |
      | record.ready      | false                            |

  Scenario: One Player is ready
    Given a ben.risk.irs.JoinLobby message has been received with:
      | playerName | Ben |

    When a ben.risk.irs.Ready message is received with:
      | playerName | Ben |

    Then a ben.risk.irs.record.WriteRecord message is sent to DATA_STORE with:
      | record.type       | ben.risk.irs.player.PlayerRecord |
      | record.id         | 0                                |
      | record.playerName | Ben                              |
      | record.ready      | true                             |

  Scenario: All Players are ready
    Given a ben.risk.irs.JoinLobby message has been received with:
      | playerName | Ben |

    And a ben.risk.irs.JoinLobby message has been received with:
      | playerName | Simon |

    And a ben.risk.irs.JoinLobby message has been received with:
      | playerName | Wu |

    When a ben.risk.irs.Ready message is received with:
      | playerName | Ben |

    And a ben.risk.irs.Ready message is received with:
      | playerName | Simon |

    And a ben.risk.irs.Ready message is received with:
      | playerName | Wu |

    Then a ben.risk.irs.record.WriteRecord message is sent to DATA_STORE with:
      | record.type      | ben.risk.irs.game.GameRecord |
      | record.id        | 0                            |
      | record.gameState | TRADING                      |