package ben.risk.irs.record;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Write Record.
 * <p>
 *     Message to send to the Data Store to update a record.
 * </p>
 */
@Immutable
public final class WriteRecord implements Serializable {

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

    @Override
    public String toString() {
        return "WriteRecord{" +
                "record=" + record +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WriteRecord that = (WriteRecord) o;

        return record.equals(that.record);
    }

    @Override
    public int hashCode() {
        return record.hashCode();
    }
}
