package ben.risk.irs.record;

import java.io.Serializable;

/**
 * Delete All Records.
 */
public final class DeleteAllRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<?> type;

    public DeleteAllRecords(Class<?> type) {
        this.type = type;
    }

    public final Class<?> getType() {
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
