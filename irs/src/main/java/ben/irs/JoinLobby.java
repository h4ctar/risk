package ben.irs;

import java.io.Serializable;

/**
 * Join Lobby.
 */
public final class JoinLobby implements Serializable {

    private final String playerName;

    public JoinLobby(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
