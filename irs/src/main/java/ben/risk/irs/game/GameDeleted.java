package ben.risk.irs.game;

import java.io.Serializable;

/**
 * Game Deleted.
 */
public class GameDeleted implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private final int id;

    /**
     * Constructor.
     * @param id the ID of the record that was deleted
     */
    public GameDeleted(int id) {
        this.id = id;
    }

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
