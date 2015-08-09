package ben.risk.irs;

import ben.risk.irs.game.GameDeleted;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Game Record Deleted Factory.
 */
public class GameRecordDeletedFactory implements IMessageFactory<GameDeleted> {

    @NotNull
    @Override
    public String getMessageName() {
        return "GAME_RECORD_DELETED";
    }

    @NotNull
    @Override
    public Class<GameDeleted> getMessageClass() {
        return GameDeleted.class;
    }

    @NotNull
    @Override
    public GameDeleted createMessage(@NotNull Map<String, String> values) {
        return new GameDeleted(getRecordId(values));
    }

    private int getRecordId(@NotNull Map<String, String> values) {
        return values.containsKey("RECORD_ID") ? Integer.valueOf(values.get("RECORD_ID")) : 0;
    }
}
