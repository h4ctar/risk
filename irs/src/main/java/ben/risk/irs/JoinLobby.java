package ben.risk.irs;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Join Lobby.
 * <p>
 *     Message sent from a player to the game master when they want to join the lobby.
 * </p>
 */
@Immutable
public final class JoinLobby implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The player name.
     */
    @NotNull
    private final String playerName;

    /**
     * Constructor.
     * @param playerName the name of the player who wants to join the lobby
     */
    public JoinLobby(@NotNull String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get the name of the player who wants to join the lobby.
     * @return the name of the player who wants to join the lobby
     */
    @NotNull
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinLobby joinLobby = (JoinLobby) o;

        return playerName.equals(joinLobby.playerName);
    }

    @Override
    public int hashCode() {
        return playerName.hashCode();
    }
}
