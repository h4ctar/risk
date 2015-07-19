package ben.risk.master.clock;

import ben.irs.client.ClientNames;
import ben.irs.mom.TimeUpdate;
import ben.mom.client.MomClient;

/**
 * Clock.
 * <p>
 *     Sends out a periodic time update message.
 * </p>
 */
public class Clock {

    /**
     * The MOM client.
     */
    private final MomClient momClient;

    /**
     * Is the clock running?
     */
    private boolean running = true;

    /**
     * Constructor.
     * @param momClient the MOM Client
     */
    public Clock(MomClient momClient) {
        this.momClient = momClient;

        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(), "Shutdown Hook"));
        new Thread(new ClockUpdateSender()).start();
    }

    /**
     * Stop the Clock.
     */
    public void stop() {
        running = false;
    }

    private class ClockUpdateSender implements Runnable {

        @Override
        public void run() {
            try {
                while (running) {
                    momClient.sendMessage(ClientNames.ALL, new TimeUpdate(System.currentTimeMillis()));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ShutdownHook implements Runnable {

        @Override
        public void run() {
            stop();
        }
    }
}
