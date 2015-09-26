package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

@Immutable
public class RequestAllRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<?> type;

    public RequestAllRecords(Class<?> type) {
        this.type = type;
    }

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
