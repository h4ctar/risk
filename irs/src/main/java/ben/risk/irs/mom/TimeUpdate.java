package ben.risk.irs.mom;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

/**
 * Time Update.
 */
@Immutable
public class TimeUpdate implements Serializable {

    private final long time;

    public TimeUpdate(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
