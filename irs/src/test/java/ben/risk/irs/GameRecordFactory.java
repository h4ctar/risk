package ben.risk.irs;

import ben.risk.irs.game.GameRecord;
import ben.risk.irs.type.GameStateName;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Game Record Factory.
 */
public class GameRecordFactory implements IRecordFactory<GameRecord> {

    @NotNull
    @Override
    public GameRecord createRecord(@NotNull Map<String, String> values) {
        return new GameRecord(getGameStateName(values));
    }

    @NotNull
    private GameStateName getGameStateName(@NotNull Map<String, String> values) {
        return values.containsKey("STATE_NAME") ? GameStateName.valueOf(values.get("STATE_NAME")) : GameStateName.LOBBY;
    }
}
