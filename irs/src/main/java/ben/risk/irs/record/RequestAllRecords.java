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
}
