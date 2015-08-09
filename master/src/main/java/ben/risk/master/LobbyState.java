package ben.risk.master;

import ben.risk.irs.JoinLobby;
import ben.risk.irs.Ready;
import ben.mom.client.IMessageProcessor;
import ben.mom.client.IMomClient;
import org.jetbrains.annotations.NotNull;

/**
 * Lobby State.
 */
public final class LobbyState implements IGameState {

    public LobbyState(IMomClient momClient) {
        momClient.subscribe(JoinLobby.class, new JoinLobbyProcessor());
        momClient.subscribe(Ready.class, new ReadyProcessor());
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    private class JoinLobbyProcessor implements IMessageProcessor<JoinLobby> {

        @Override
        public void processMessage(@NotNull JoinLobby joinLobby) {
            System.out.println("Join Lobby");
        }
    }

    private class ReadyProcessor implements IMessageProcessor<Ready> {

        @Override
        public void processMessage(@NotNull Ready ready) {
            System.out.println("Ready");
        }
    }
}
