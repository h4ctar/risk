package ben.risk.irs;

import ben.risk.irs.record.IRecord;
import ben.risk.irs.record.RequestAllRecords;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Request All Records Factory.
 */
public class RequestAllRecordsFactory implements IMessageFactory<RequestAllRecords> {

    private final String messageName;

    private final Class<? extends IRecord> recordType;

    public RequestAllRecordsFactory(String messageName, Class<? extends IRecord> recordType) {
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
    public Class<RequestAllRecords> getMessageClass() {
        return RequestAllRecords.class;
    }

    @NotNull
    @Override
    public RequestAllRecords createMessage(@NotNull Map<String, String> values) {
        return new RequestAllRecords(recordType);
    }
}
