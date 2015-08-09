Feature: Data Store

  Scenario Outline: Write a new record
    When a <write_record> message is received
    Then a <record_updated> message is sent to ALL

      Examples:
        | write_record      | record_updated      |
        | WRITE_GAME_RECORD | GAME_RECORD_UPDATED |

  Scenario Outline: Delete an existing record
    Given a <write_record> message has been received
    When a <delete_record> message is received
    Then a <record_deleted> message is sent to ALL

    Examples:
      | write_record      | delete_record      | record_deleted      |
      | WRITE_GAME_RECORD | DELETE_GAME_RECORD | GAME_RECORD_DELETED |

  Scenario Outline: Request all records
    Given a <write_record> message has been received
    When a <request_all_records> message is received
    Then a <record_updated> message is sent to ALL

    Examples:
      | write_record      | request_all_records      | record_updated      |
      | WRITE_GAME_RECORD | REQUEST_ALL_GAME_RECORDS | GAME_RECORD_UPDATED |

  Scenario Outline: Delete all records
    Given a <write_record> message has been received
    When a <delete_all_records> message is received
    Then a <record_deleted> message is sent to ALL

    Examples:
      | write_record      | delete_all_records      | record_deleted      |
      | WRITE_GAME_RECORD | DELETE_ALL_GAME_RECORDS | GAME_RECORD_DELETED |
