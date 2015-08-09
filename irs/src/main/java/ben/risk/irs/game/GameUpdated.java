package ben.risk.irs.game;

import java.io.Serializable;

/**
 * Game Updated.
 */
public class GameUpdated implements Serializable {

    private final GameRecord record;

    public GameUpdated(GameRecord record) {
        this.record = record;
    }

    public GameRecord getRecord() {
        return record;
    }
}
