package ben.risk.player;

import ben.irs.JoinLobby;
import ben.irs.client.ClientNames;
import ben.irs.record.CurrentGameState;
import ben.mom.client.IEventQueue;
import ben.mom.client.IMessageProcessor;
import ben.mom.client.MomClient;
import ben.risk.player.lobby.LobbyPane;
import ben.risk.player.login.LoginPane;
import ben.ui.widget.IWidget;
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
public final class PlayerWindow extends MainWindow {

    @Nullable
    private LobbyPane lobbyPane = new LobbyPane();

    /**
     * The MOM Client.
     */
    @Nullable
    private MomClient momClient;

    public void init() {
        getActionManager().addActionFactory(JoinLobbyAction.class, new JoinLobbyAction(this));

        setRootWidget(new LoginPane(getActionManager()).getPane());
    }

    /**
     * Join a game lobby.
     * @param address the address of the MOM Server
     * @param port the port of the MOM Server
     * @param playerName the name of the player
     */
    public void joinLobby(@NotNull String address, int port, @NotNull String playerName) throws IOException {
        assert momClient == null : "MOM client should not be created yet";
        momClient = new MomClient(playerName, address, port, new EventQueue());

        momClient.subscribe(CurrentGameState.class, new CurrentGameStateProcessor());
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

    private class CurrentGameStateProcessor implements IMessageProcessor<CurrentGameState> {

        @Override
        public void processMessage(@NotNull CurrentGameState currentGameState) {
            IWidget rootWidget = null;
            switch (currentGameState.getGameState()) {
            case LOBBY:
                assert lobbyPane != null : "Init must have been called first";
                rootWidget = lobbyPane.getPane();
                break;

            case TRADING:
            case PLACING_ARMIES:
            case ATTACKING:
            case FORTIFYING:
                break;
            }
            setRootWidget(rootWidget);
        }
    }
}
