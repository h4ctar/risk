Feature: Getting armies

  Scenario Outline: Drafting
    Given the current player owns all the territories in <continents_owned> and <extra_territories>
      | continent      | territories                                                                                                                       |
      |  North America |  Alaska, Alberta, Central America, Eastern United States, Greenland, Northwest, Territory, Ontario, Quebec, Western United States |
      |  South America |  Argentina, Brazil, Peru, Venezuela                                                                                               |
      |  Europe        |  Great Britain, Iceland, Northern Europe, Scandinavia, Southern Europe, Ukraine, Western Europe                                   |
      |  Africa        |  Congo, East Africa, Egypt, Madagascar, North Africa, South Africa                                                                |
      |  Asia          |  Afghanistan, China, India, Irkutsk, Japan, Kamchatka, Middle East, Mongolia, Siam, Siberia, Ural, Yakutsk                        |
      |  Australia     |  Eastern Australia, Indonesia, New Guinea, Western Australia                                                                      |
    When the system enters the 'TRADING' state
    Then the current player gets min(floor(number_of_territories_owned / 3), 3) + continent_bonus = <armies> armies
        | continent      | continent_bonus |
        |  North America |  5              |
        |  South America |  2              |
        |  Europe        |  5              |
        |  Africa        |  3              |
        |  Asia          |  7              |
        |  Australia     |  2              |

      Examples:
        | continents_owned | extra_territories     | armies |
        |  Africa          |                       |  6     |
        |  Asia and Europe |  Alaska and Greenland |  19    |

  Scenario: Trading
    Given the system is in the 'TRADING' state
    And the current player has at least 3 cards of the same rank
    When the current player trades a set of 3 cards
    Then the system allocates the armies from the traded cards
    And the system moves to the 'PLACING_ARMIES' state