package ben.risk.irs;

import ben.risk.irs.game.GameRecord;
import ben.risk.irs.game.GameUpdated;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Game Record Updated Factory.
 */
public class GameRecordUpdatedFactory implements IMessageFactory<GameUpdated> {

    private final IRecordFactory<GameRecord> recordFactory;

    public GameRecordUpdatedFactory(IRecordFactory<GameRecord> recordFactory) {
        this.recordFactory = recordFactory;
    }

    @NotNull
    @Override
    public String getMessageName() {
        return "GAME_RECORD_UPDATED";
    }

    @NotNull
    @Override
    public Class<GameUpdated> getMessageClass() {
        return GameUpdated.class;
    }

    @NotNull
    @Override
    public GameUpdated createMessage(@NotNull Map<String, String> values) {
        GameRecord gameRecord = recordFactory.createRecord(values);
        return new GameUpdated(gameRecord);
    }
}
