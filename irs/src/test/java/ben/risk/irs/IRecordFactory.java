package ben.risk.irs;

import ben.risk.irs.record.IRecord;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Record Factory Interface.
 */
public interface IRecordFactory<T extends IRecord> {

    @NotNull
    T createRecord(@NotNull Map<String, String> values);
}
