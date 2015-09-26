package ben.risk.irs.game;

import ben.risk.irs.record.IRecord;
import ben.risk.irs.type.GameState;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Current Game State.
 */
public final class GameRecord implements IRecord, Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    private final GameState gameState;

    private final int id;

    public GameRecord(int id, @NotNull GameState gameState) {
        this.id = id;
        this.gameState = gameState;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "gameState=" + gameState +
                ", id=" + id +
                '}';
    }

    @NotNull
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getRecordId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameRecord that = (GameRecord) o;

        if (id != that.id) return false;
        return gameState == that.gameState;

    }

    @Override
    public int hashCode() {
        int result = gameState.hashCode();
        result = 31 * result + id;
        return result;
    }
}
