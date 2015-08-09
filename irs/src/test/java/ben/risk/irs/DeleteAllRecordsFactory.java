package ben.risk.irs;

import ben.risk.irs.record.DeleteAllRecords;
import ben.risk.irs.record.IRecord;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Delete All Records Factory.
 */
public class DeleteAllRecordsFactory implements IMessageFactory<DeleteAllRecords> {

    private final String messageName;

    private final Class<? extends IRecord> recordType;

    public DeleteAllRecordsFactory(String messageName, Class<? extends IRecord> recordType) {
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
    public Class<DeleteAllRecords> getMessageClass() {
        return DeleteAllRecords.class;
    }

    @NotNull
    @Override
    public DeleteAllRecords createMessage(@NotNull Map<String, String> values) {
        return new DeleteAllRecords(recordType);
    }
}
