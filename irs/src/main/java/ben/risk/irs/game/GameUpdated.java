package ben.risk.irs.game;

import java.io.Serializable;

/**
 * Game Updated.
 */
public class GameUpdated implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private final GameRecord record;

    public GameUpdated(GameRecord record) {
        this.record = record;
    }

    public GameRecord getRecord() {
        return record;
    }
}
