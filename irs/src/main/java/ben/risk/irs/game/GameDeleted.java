package ben.risk.irs.game;

import java.io.Serializable;

/**
 * Game Deleted.
 * <p>
 *     Message that is broadcasted by the data store when a game record is deleted.
 * </p>
 */
public final class GameDeleted implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The ID of the game that is deleted.
     */
    private final int id;

    /**
     * Constructor.
     * @param id the ID of the record that was deleted
     */
    public GameDeleted(int id) {
        this.id = id;
    }

    /**
     * Get the ID of the game that is deleted.
     * @return the ID of the game that is deleted
     */
    public int getRecordId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameDeleted that = (GameDeleted) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
