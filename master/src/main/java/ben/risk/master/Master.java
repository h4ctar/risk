package ben.risk.master;

import ben.risk.irs.client.ClientNames;
import ben.risk.irs.game.GameRecord;
import ben.risk.irs.player.PlayerRecord;
import ben.risk.irs.record.WriteRecord;
import ben.mom.client.IMomClient;
import ben.risk.irs.type.GameState;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Master.
 */
public final class Master implements IMaster {

    @NotNull
    private static final Logger LOGGER = LogManager.getLogger(Master.class);

    @NotNull
    private final IMomClient momClient;

    @NotNull
    private final LobbyGameState lobbyState;

    @NotNull
    private final TradingGameState tradingState;

    @NotNull
    private final PlacingGameState placingState;

    @NotNull
    private final AttackingGameState attackingState;

    @NotNull
    private final FortifyingGameState fortifyingState;

    @Nullable
    private GameStateName gameState;

    @Nullable
    private Player[] players;

    private Player currentPlayer;

    /**
     * Constructor.
     * @param momClient the MOM Client
     */
    public Master(@NotNull IMomClient momClient) {
        this.momClient = momClient;

        lobbyState = new LobbyGameState(momClient, this);
        tradingState = new TradingGameState();
        placingState = new PlacingGameState();
        attackingState = new AttackingGameState();
        fortifyingState = new FortifyingGameState();

        startLobby();
    }

    @Override
    public void startLobby() {
        LOGGER.info("Starting lobby");

        gameState = GameStateName.LOBBY;
        lobbyState.start();

        writeGameState();
    }

    @Override
    public void startGame(Player[] players) {
        LOGGER.info("Starting game");

        this.players = players;

        lobbyState.stop();

        // TODO: Setup game
        currentPlayer = getFirstPlayer();

        gameState = GameStateName.TRADING;
        tradingState.start();

        writeGameState();
    }

    private void writeGameState() {
        assert gameState != null;
        GameState irsGameState = gameState.getIrsGameState();
        momClient.sendMessage(ClientNames.DATA_STORE, new WriteRecord(new GameRecord(0, irsGameState)));
    }

    @NotNull
    private Player getFirstPlayer() {
        assert players != null;
        Player firstPlayer = null;
        for (Player player : players) {
            if (player != null) {
                firstPlayer = player;
                break;
            }
        }
        assert firstPlayer != null;
        return firstPlayer;
    }
}
