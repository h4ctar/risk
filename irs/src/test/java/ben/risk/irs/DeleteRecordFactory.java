package ben.risk.irs;

import ben.risk.irs.record.DeleteRecord;
import ben.risk.irs.record.IRecord;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Delete Record Factory.
 */
public class DeleteRecordFactory implements IMessageFactory<DeleteRecord> {

    private final String messageName;

    private final Class<? extends IRecord> recordType;

    public DeleteRecordFactory(String messageName, Class<? extends IRecord> recordType) {
        this.messageName = messageName;
        this.recordType = recordType;
    }

    @NotNull
    @Override
    public String getMessageName() {
        return messageName;
    }

    @NotNull
    @Override
    public Class<DeleteRecord> getMessageClass() {
        return DeleteRecord.class;
    }

    @NotNull
    @Override
    public DeleteRecord createMessage(@NotNull Map<String, String> values) {
        return new DeleteRecord(recordType, getRecordId(values));
    }

    private int getRecordId(@NotNull Map<String, String> values) {
        return values.containsKey("RECORD_ID") ? Integer.valueOf(values.get("RECORD_ID")) : 0;
    }
}
