package ben.risk.irs.record;

import java.io.Serializable;

/**
 * Delete All Records.
 * <p>
 *     Message to send to the data store to delete all records of a type.
 * </p>
 */
public final class DeleteAllRecords implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The type of record to delete.
     */
    private final Class<?> type;

    /**
     * Constructor.
     * @param type the type of record to delete.
     */
    public DeleteAllRecords(Class<?> type) {
        this.type = type;
    }

    /**
     * Get the type of record to delete.
     * @return the type of record to delete
     */
    public Class<?> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteAllRecords that = (DeleteAllRecords) o;

        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
