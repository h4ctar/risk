package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Delete Record.
 * <p>
 *     Message that is sent to the data store to delete a particular record.
 * </p>
 */
@Immutable
public final class DeleteRecord implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The type of record to delete.
     */
    private final Class<?> type;

    /**
     * The ID of the record to delete.
     */
    private final int id;

    /**
     * Constructor.
     * @param type the type of record to delete
     * @param id the ID of the record to delete
     */
    public DeleteRecord(Class<?> type, int id) {
        this.type = type;
        this.id = id;
    }

    /**
     * Get the type of record to delete.
     * @return the type of record to delete
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Get the ID of the record to delete.
     * @return the ID of the record to delete
     */
    public int getRecordId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteRecord that = (DeleteRecord) o;

        if (id != that.id) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
