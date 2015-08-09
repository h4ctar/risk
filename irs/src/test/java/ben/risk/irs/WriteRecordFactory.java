package ben.risk.irs;

import ben.risk.irs.record.IRecord;
import ben.risk.irs.record.WriteRecord;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Write Record Factory.
 */
public class WriteRecordFactory implements IMessageFactory<WriteRecord> {

    @NotNull
    private final String messageName;

    @NotNull
    private final IRecordFactory recordFactory;

    public WriteRecordFactory(@NotNull String messageName, @NotNull IRecordFactory recordFactory) {
        this.messageName = messageName;
        this.recordFactory = recordFactory;
    }
    
    @NotNull
    @Override
    public String getMessageName() {
        return messageName;
    }

    @NotNull
    @Override
    public Class<WriteRecord> getMessageClass() {
        return WriteRecord.class;
    }

    @NotNull
    @Override
    public WriteRecord createMessage(@NotNull Map<String, String> values) {
        IRecord record = recordFactory.createRecord(values);
        return new WriteRecord(record);
    }
}
