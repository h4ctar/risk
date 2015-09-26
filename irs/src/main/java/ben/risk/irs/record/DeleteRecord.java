package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

@Immutable
public final class DeleteRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<?> type;

    private final int id;

    public DeleteRecord(Class<?> type, int id) {
        this.type = type;
        this.id = id;
    }

    public final Class<?> getType() {
        return type;
    }

    public final int getRecordId() {
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
