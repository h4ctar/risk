package ben.risk.master;

import ben.risk.irs.JoinLobby;
import ben.risk.irs.Ready;
import ben.mom.client.IMessageProcessor;
import ben.mom.client.IMomClient;
import ben.risk.irs.client.ClientNames;
import ben.risk.irs.player.PlayerRecord;
import ben.risk.irs.record.WriteRecord;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Lobby State.
 */
public final class LobbyGameState {

    @NotNull
    private static final Logger LOGGER = LogManager.getLogger(LobbyGameState.class);

    private static final int MIN_NUMBER_OF_PLAYERS = 3;

    private static final int MAX_NUMBER_OF_PLAYERS = 6;

    private static final int INVALID_PLAYER = -1;

    @NotNull
    private final IMomClient momClient;

    @NotNull
    private final IMaster master;

    @NotNull
    private Player[] players = new Player[MAX_NUMBER_OF_PLAYERS];

    private boolean active = false;

    public LobbyGameState(@NotNull IMomClient momClient, @NotNull IMaster master) {
        this.momClient = momClient;
        this.master = master;

        momClient.subscribe(JoinLobby.class, new JoinLobbyProcessor());
        momClient.subscribe(Ready.class, new ReadyProcessor());
    }

    public void start() {
        assert !active;
        active = true;
    }

    public void stop() {
        assert active;
        active = false;
    }

    @Nullable
    private Player getExistingPlayer(@NotNull String playerName) {
        Player existingPlayer = null;
        for (Player player : players) {
            if (player != null && player.getPlayerName().equals(playerName)) {
                existingPlayer = player;
                break;
            }
        }
        return existingPlayer;
    }

    /**
     * Get the number of the next player.
     * @return the player number
     */
    private int getNextPlayerNumber() {
        int playerNumber = INVALID_PLAYER;
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            Player player = players[i];
            if (player == null) {
                playerNumber = i;
                break;
            }
        }
        return playerNumber;
    }

    private boolean canStartGame() {
        int numberOfPlayers = 0;
        boolean allPlayersReady = true;
        for (Player player : players) {
            if (player != null) {
                numberOfPlayers++;
                if (!player.isReady()) {
                    allPlayersReady = false;
                    break;
                }
            }
        }
        return numberOfPlayers >= MIN_NUMBER_OF_PLAYERS && allPlayersReady;
    }

    private class JoinLobbyProcessor implements IMessageProcessor<JoinLobby> {

        @Override
        public void processMessage(@NotNull JoinLobby joinLobby) {
            if (active) {
                LOGGER.info(joinLobby.getPlayerName() + " joined the lobby");

                Player player = getExistingPlayer(joinLobby.getPlayerName());

                if (player == null) {
                    int playerNumber = getNextPlayerNumber();
                    if (playerNumber == INVALID_PLAYER) {
                        LOGGER.warn("No room left for " + joinLobby.getPlayerName());
                    }
                    else {
                        player = new Player(playerNumber, joinLobby.getPlayerName());
                        players[player.getPlayerNumber()] = player;
                    }
                }
                else {
                    LOGGER.warn(joinLobby.getPlayerName() + " is an existing player");
                    player.setReady(false);
                }

                if (player != null) {
                    PlayerRecord playerRecord = new PlayerRecord(player.getPlayerNumber(), player.getPlayerName(), player.isReady());
                    momClient.sendMessage(ClientNames.DATA_STORE, new WriteRecord(playerRecord));
                }
            }
            else {
                LOGGER.warn(joinLobby.getPlayerName() + " tried to join the lobby even though the lobby is not active");
            }
        }
    }

    private class ReadyProcessor implements IMessageProcessor<Ready> {

        @Override
        public void processMessage(@NotNull Ready ready) {
            if (active) {
                LOGGER.info(ready.getPlayerName() + " is ready");

                Player player = getExistingPlayer(ready.getPlayerName());
                if (player == null) {
                    LOGGER.error("Received ready message for non existing player");
                }
                else {
                    player.setReady(true);

                    PlayerRecord playerRecord = new PlayerRecord(player.getPlayerNumber(), player.getPlayerName(), player.isReady());
                    momClient.sendMessage(ClientNames.DATA_STORE, new WriteRecord(playerRecord));
                }

                if (canStartGame()) {
                    master.startGame(players);
                }
            }
            else {
                LOGGER.warn(ready.getPlayerName() + " tried to say 'ready' even though the lobby is not active");
            }
        }
    }
}

