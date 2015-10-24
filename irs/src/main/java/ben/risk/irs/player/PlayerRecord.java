package ben.risk.irs.player;

import ben.risk.irs.record.IRecord;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Player Record.
 */
@Immutable
public final class PlayerRecord implements IRecord, Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The ID of the player.
     */
    private final int id;

    /**
     * The name of the player.
     */
    @NotNull
    private final String playerName;

    /**
     * Is the player read to play?
     * Used in the lobby.
     */
    private final boolean ready;

    /**
     * Constructor.
     * @param id the ID of the player
     * @param playerName the name of the player
     * @param ready is the player read to play?
     */
    public PlayerRecord(int id, @NotNull String playerName, boolean ready) {
        this.id = id;
        this.playerName = playerName;
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "PlayerRecord{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", ready=" + ready +
                '}';
    }

    /**
     * Get the player name.
     * @return the player name
     */
    @NotNull
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Is the player read in the lobby?
     * @return true if the player is read
     */
    public boolean isReady() {
        return ready;
    }

    @Override
    public int getRecordId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerRecord that = (PlayerRecord) o;

        if (id != that.id) return false;
        if (ready != that.ready) return false;
        return playerName.equals(that.playerName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + playerName.hashCode();
        result = 31 * result + (ready ? 1 : 0);
        return result;
    }
}
