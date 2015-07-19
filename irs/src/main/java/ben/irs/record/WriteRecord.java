package ben.irs.record;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Write Record.
 * <p>
 * Message to send to the Data Store to update a record.
 */
@Immutable
public class WriteRecord implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The record to write.
     */
    @NotNull
    private final IRecord record;

    /**
     * Constructor.
     * @param record the record to write
     */
    public WriteRecord(@NotNull IRecord record) {
        this.record = record;
    }

    /**
     * Get the record to write.
     * @return the record
     */
    @NotNull
    public final IRecord getRecord() {
        return record;
    }
}
