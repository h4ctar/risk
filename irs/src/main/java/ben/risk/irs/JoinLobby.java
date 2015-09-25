package ben.risk.irs;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Join Lobby.
 */
@Immutable
public final class JoinLobby implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private final String playerName;

    public JoinLobby(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
