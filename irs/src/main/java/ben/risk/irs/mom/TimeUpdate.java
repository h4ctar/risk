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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeUpdate that = (TimeUpdate) o;

        return time == that.time;

    }

    @Override
    public int hashCode() {
        return (int) (time ^ (time >>> 32));
    }
}
