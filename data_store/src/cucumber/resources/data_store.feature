Feature: Data Store

  Scenario Outline: Write a new record
    When a ben.risk.irs.record.WriteRecord message is received with:
      | record.type | <record_type> |
      | record.id   | 0             |

    Then a <record_updated> message is sent to ALL

      Examples:
        | record_type                       | record_updated                     |
        |  ben.risk.irs.game.GameRecord     |  ben.risk.irs.game.GameUpdated     |
        |  ben.risk.irs.player.PlayerRecord |  ben.risk.irs.player.PlayerUpdated |

  Scenario Outline: Delete an existing record
    Given a ben.risk.irs.record.WriteRecord message has been received with:
      | record.type | <record_type> |
      | record.id   | 0             |

    When a ben.risk.irs.record.DeleteRecord message is received with:
      | type | <record_type> |
      | id   | 0             |

    Then a <record_deleted> message is sent to ALL

    Examples:
      | record_type                       | record_deleted                     |
      |  ben.risk.irs.game.GameRecord     |  ben.risk.irs.game.GameDeleted     |
      |  ben.risk.irs.player.PlayerRecord |  ben.risk.irs.player.PlayerDeleted |

  Scenario Outline: Request all records
    Given a ben.risk.irs.record.WriteRecord message has been received with:
      | record.type | <record_type> |
      | record.id   | 0             |

    When a ben.risk.irs.record.RequestAllRecords message is received with:
      | type | <record_type> |

    Then a <record_updated> message is sent to ALL

    Examples:
      | record_type                       | record_updated                     |
      |  ben.risk.irs.game.GameRecord     |  ben.risk.irs.game.GameUpdated     |
      |  ben.risk.irs.player.PlayerRecord |  ben.risk.irs.player.PlayerUpdated |

  Scenario Outline: Delete all records
    Given a ben.risk.irs.record.WriteRecord message has been received with:
      | record.type | <record_type> |
      | record.id   | 0             |

    When a ben.risk.irs.record.DeleteAllRecords message is received with:
      | type | <record_type> |

    Then a <record_deleted> message is sent to ALL

    Examples:
      | record_type                       | record_deleted                     |
      |  ben.risk.irs.game.GameRecord     |  ben.risk.irs.game.GameDeleted     |
      |  ben.risk.irs.player.PlayerRecord |  ben.risk.irs.player.PlayerDeleted |
