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
}
