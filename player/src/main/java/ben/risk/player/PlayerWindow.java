package ben.risk.player;

import ben.risk.irs.JoinLobby;
import ben.risk.irs.client.ClientNames;
import ben.risk.irs.game.GameRecord;
import ben.risk.irs.game.GameUpdated;
import ben.risk.irs.record.RequestAllRecords;
import ben.mom.client.IEventQueue;
import ben.mom.client.IMessageProcessor;
import ben.mom.client.MomClient;
import ben.risk.player.game.GamePane;
import ben.risk.player.lobby.LobbyPane;
import ben.risk.player.login.LoginPane;
import ben.ui.window.MainWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Player Window.
 * <p>
 *      The main window for the player client.
 * </p>
 */
public final class PlayerWindow extends MainWindow implements IPlayerWindow {

    @Nullable
    private LoginPane loginPane;

    @Nullable
    private LobbyPane lobbyPane;

    @Nullable
    private GamePane gamePane;

    /**
     * The MOM Client.
     */
    @Nullable
    private MomClient momClient;

    @Nullable
    private String playerName;

    /**
     * Initialise the window.
     */
    public void init() {
        setRootWidget(getLoginPane().getPane());
    }

    @NotNull
    private LoginPane getLoginPane() {
        if (loginPane == null) {
            loginPane = new LoginPane(this);
        }
        return loginPane;
    }

    @NotNull
    private LobbyPane getLobbyPane() {
        assert momClient != null : "MOM Client should be initialised before the lobby is joined";
        assert playerName != null : "Player name should be set before the lobby is joined";

        if (lobbyPane == null) {
            lobbyPane = new LobbyPane(playerName, momClient);
        }
        return lobbyPane;
    }

    @NotNull
    private GamePane getGamePane() {
        if (gamePane == null) {
            gamePane = new GamePane();
        }
        return gamePane;
    }

    @Override
    public void joinLobby(@NotNull String address, int port, @NotNull String playerName) throws IOException {
        this.playerName = playerName;

        assert momClient == null : "MOM client should not be created yet";
        momClient = new MomClient(playerName, address, port, new EventQueue());

        momClient.subscribe(GameUpdated.class, new GameUpdatedProcessor());
        momClient.sendMessage(ClientNames.DATA_STORE, new RequestAllRecords(GameRecord.class));
        momClient.sendMessage(ClientNames.MASTER, new JoinLobby(playerName));
    }

    @Override
    public void stop() {
        if (momClient != null) {
            momClient.stop();
            momClient = null;
        }
        super.stop();
    }

    /**
     * The event queue.
     * <p>
     *     Forwards message processing from the MOM Client onto the EDT thread.
     * </p>
     */
    private class EventQueue implements IEventQueue {

        @Override
        public void invoke(@NotNull Runnable runnable) {
            invokeOnEdt(runnable);
        }
    }

    private class GameUpdatedProcessor implements IMessageProcessor<GameUpdated> {

        @Override
        public void processMessage(@NotNull GameUpdated gameUpdated) {
            GameRecord gameRecord = gameUpdated.getRecord();

            switch (gameRecord.getGameState()) {
            case LOBBY:
                setRootWidget(getLobbyPane().getPane());
                break;

            case TRADING:
            case PLACING:
            case ATTACKING:
            case FORTIFYING:
                setRootWidget(getGamePane().getPane());
                break;
            }
        }
    }
}
