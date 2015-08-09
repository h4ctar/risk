package ben.risk.irs.game;

import ben.risk.irs.record.IRecord;
import ben.risk.irs.type.GameStateName;

import java.io.Serializable;

/**
 * Current Game State.
 */
public class GameRecord implements IRecord, Serializable {

    private final GameStateName gameStateName;

    public GameRecord(GameStateName gameStateName) {
        this.gameStateName = gameStateName;
    }

    public GameStateName getGameStateName() {
        return gameStateName;
    }

    @Override
    public int getRecordId() {
        return 0;
    }
}
