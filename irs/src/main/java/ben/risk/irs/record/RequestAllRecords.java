package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Request All Records.
 * <p>
 *     Message that is sent to the data store to request all records.
 * </p>
 */
@Immutable
public class RequestAllRecords implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The type of record to get updates for.
     */
    private final Class<?> type;

    /**
     * Constructor.
     * @param type the type of record
     */
    public RequestAllRecords(Class<?> type) {
        this.type = type;
    }

    /**
     * Get the type of record.
     * @return the type of record
     */
    public final Class<?> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestAllRecords that = (RequestAllRecords) o;

        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
