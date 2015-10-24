package ben.risk.irs.game;

import java.io.Serializable;

/**
 * Game Updated.
 * <p>
 *     Message that is broadcasted by the data store when a game record is updated.
 * </p>
 */
public final class GameUpdated implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The game record.
     */
    private final GameRecord record;

    /**
     * Constructor.
     * @param record the game record
     */
    public GameUpdated(GameRecord record) {
        this.record = record;
    }

    /**
     * Get the game record.
     * @return the game record
     */
    public GameRecord getRecord() {
        return record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameUpdated that = (GameUpdated) o;

        return !(record != null ? !record.equals(that.record) : that.record != null);

    }

    @Override
    public int hashCode() {
        return record != null ? record.hashCode() : 0;
    }
}
