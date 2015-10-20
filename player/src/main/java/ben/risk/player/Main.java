package ben.risk.player;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Main class.
 */
public final class Main {

    /**
     * The player window.
     */
    @Nullable
    private PlayerWindow playerWindow;

    /**
     * Main method.
     * @param args program args
     * @throws IOException the message broker failed to connect to the simulator
     */
    public static void main(String[] args) throws IOException {
        new Main().start(args);
    }

    /**
     * Start the application.
     * @param args program args
     */
    public void start(String[] args) {
        playerWindow = new PlayerWindow();
        playerWindow.init();
    }

    /**
     * Stop the application.
     */
    public void stop() {
        if (playerWindow != null) {
            playerWindow.stop();
            playerWindow = null;
        }
    }

    public PlayerWindow getPlayerWindow() {
        return playerWindow;
    }
}
