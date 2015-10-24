package ben.risk.irs;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Ready.
 * <p>
 *     Message sent from players to the game master when they are in the lobby and ready to play.
 * </p>
 */
@Immutable
public final class Ready implements Serializable {

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
     * @param playerName the name of the player that is ready
     */
    public Ready(@NotNull String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get the player name.
     * @return the player name
     */
    @NotNull
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ready ready = (Ready) o;

        return playerName.equals(ready.playerName);
    }

    @Override
    public int hashCode() {
        return playerName.hashCode();
    }
}
