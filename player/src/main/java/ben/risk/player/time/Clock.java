package ben.risk.player.time;

import ben.irs.mom.TimeUpdate;
import ben.mom.client.IMomClient;
import ben.mom.client.IMessageProcessor;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * Clock.
 */
@ThreadSafe
public class Clock {

    private long offset = 0;

    /**
     * Constructor.
     * @param momClient the MOM Client
     */
    public Clock(@NotNull IMomClient momClient) {
        momClient.subscribe(TimeUpdate.class, new TimeUpdateProcessor());
    }

    /**
     * Get the current time.
     * @return the current time
     */
    public long getCurrentTime() {
        return System.currentTimeMillis() + offset;
    }

    private class TimeUpdateProcessor implements IMessageProcessor<TimeUpdate> {

        @Override
        public void processMessage(@NotNull TimeUpdate timeUpdate) {
            offset = timeUpdate.getTime() - System.currentTimeMillis();
        }
    }
}
