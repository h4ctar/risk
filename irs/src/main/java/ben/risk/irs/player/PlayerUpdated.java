package ben.risk.irs.player;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Player Updated.
 */
@Immutable
public final class PlayerUpdated implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The player record.
     */
    @NotNull
    private final PlayerRecord record;

    /**
     * Constructor.
     * @param record the player record
     */
    public PlayerUpdated(@NotNull PlayerRecord record) {
        this.record = record;
    }

    /**
     * Get the player record.
     * @return the record
     */
    @NotNull
    public PlayerRecord getRecord() {
        return record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerUpdated that = (PlayerUpdated) o;

        return record.equals(that.record);
    }

    @Override
    public int hashCode() {
        return record.hashCode();
    }
}
