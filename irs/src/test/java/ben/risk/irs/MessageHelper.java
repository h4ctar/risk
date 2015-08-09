package ben.risk.irs;

import ben.risk.irs.game.GameRecord;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Message Helper.
 */
public final class MessageHelper {

    private Map<String, IMessageFactory> messageClasses = new HashMap<>();

    public MessageHelper() {
        addFactory(new WriteRecordFactory("WRITE_GAME_RECORD", new GameRecordFactory()));
        addFactory(new DeleteRecordFactory("DELETE_GAME_RECORD", GameRecord.class));
        addFactory(new DeleteAllRecordsFactory("DELETE_ALL_GAME_RECORDS", GameRecord.class));
        addFactory(new RequestAllRecordsFactory("REQUEST_ALL_GAME_RECORDS", GameRecord.class));
        addFactory(new GameRecordUpdatedFactory(new GameRecordFactory()));
        addFactory(new GameRecordDeletedFactory());
    }

    private void addFactory(@NotNull IMessageFactory factory) {
        messageClasses.put(factory.getMessageName(), factory);
    }

    @NotNull
    public Class<?> getMessageClass(@NotNull String messageName) throws Exception {
        if (!messageClasses.containsKey(messageName)) {
            throw new Exception("Message class not registered");
        }
        return messageClasses.get(messageName).getMessageClass();
    }

    @NotNull
    public Serializable createMessage(@NotNull String messageName, @NotNull Map<String, String> values) {
        return messageClasses.get(messageName).createMessage(values);
    }
}
