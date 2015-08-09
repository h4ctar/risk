package ben.risk.irs.game;

import java.io.Serializable;

/**
 * Game Deleted.
 */
public class GameDeleted implements Serializable {

    /**
     * The ID of the record that was deleted.
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
     * Get the ID of the record that was deleted.
     * @return the ID of the record that was deleted
     */
    public int getId() {
        return id;
    }
}
