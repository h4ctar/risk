package ben.risk.irs.game;

import ben.risk.irs.record.IRecord;
import ben.risk.irs.type.GameState;

import java.io.Serializable;

/**
 * Current Game State.
 */
public class GameRecord implements IRecord, Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private final GameState gameState;

    private final int id;

    public GameRecord(int id, GameState gameState) {
        this.id = id;
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getRecordId() {
        return id;
    }
}
