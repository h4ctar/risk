Feature: Setup

  Scenario: Shuffle cards
    When all players are ready to play
    Then the system shuffles the 44 cards
    And the deck contains one of each of the following cards:
      | territory              | rank      |
      |  Afghanistan           |  Infantry  |
      |  Alaska                |  Infantry  |
      |  Alberta               |  Infantry  |
      |  Argentina             |  Infantry  |
      |  Brazil                |  Artillery |
      |  Central America       |  Calvary   |
      |  China                 |  Calvary   |
      |  Congo                 |  Calvary   |
      |  East Africa           |  Artillery |
      |  Eastern Australia     |  Infantry  |
      |  Eastern United States |  Artillery |
      |  Egypt                 |  Infantry  |
      |  Great Britain         |  Calvary   |
      |  Greenland             |  Calvary   |
      |  Iceland               |  Infantry  |
      |  India                 |  Infantry  |
      |  Indonesia             |  Calvary   |
      |  Irkutsk               |  Infantry  |
      |  Japan                 |  Infantry  |
      |  Kamchatka             |  Calvary   |
      |  Madagascar            |  Infantry  |
      |  Middle East           |  Artillery |
      |  Mongolia              |  Artillery |
      |  New Guinea            |  Calvary   |
      |  North Africa          |  Infantry  |
      |  Northern Europe       |  Calvary   |
      |  Northwest Territories |  Artillery |
      |  Ontario               |  Calvary   |
      |  Peru                  |  Calvary   |
      |  Quebec                |  Artillery |
      |  Scandinavia           |  Artillery |
      |  Siam                  |  Artillery |
      |  Siberia               |  Artillery |
      |  South Africa          |  Artillery |
      |  Southern Europe       |  Calvary   |
      |  Ukraine               |  Artillery |
      |  Ural                  |  Calvary   |
      |  Venezuela             |  Artillery |
      |  Western Australia     |  Artillery |
      |  Western Europe        |  Infantry  |
      |  Western United States |  Infantry  |
      |  Yakutsk               |  Calvary   |
    And the deck contains two wild cards

  Scenario Outline: Allocate infantry
    Given there are <players> players
    When the game starts
    Then the system allocates <infantry> infantry to each player

  Examples:
  | players | infantry |
  |  3      |  35      |
  |  4      |  30      |
  |  5      |  25      |
  |  6      |  20      |

  Scenario: Distribute territories
    When all players are ready to play
    Then the system randomly allocates all the territories to the players
    And the system places one infantry on each territory from the player that owns the territory

  Scenario: Select starting player
    When all players are ready to play
    Then the system selects a random player to be the current player