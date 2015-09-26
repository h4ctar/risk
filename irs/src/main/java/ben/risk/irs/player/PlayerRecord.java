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

    private final int id;

    @NotNull
    private final String playerName;

    private final boolean ready;

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

    @NotNull
    public String getPlayerName() {
        return playerName;
    }

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
