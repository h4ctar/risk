package ben.risk.irs.record;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

@Immutable
public class DeleteRecord implements Serializable {

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
}
