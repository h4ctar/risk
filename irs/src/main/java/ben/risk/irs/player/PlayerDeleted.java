package ben.risk.irs.player;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Player Deleted.
 */
@Immutable
public final class PlayerDeleted implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The ID of the record that was deleted.
     */
    private final int id;

    /**
     * Constructor.
     * @param id the ID of the record that was deleted
     */
    public PlayerDeleted(int id) {
        this.id = id;
    }

    public int getRecordId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerDeleted that = (PlayerDeleted) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
