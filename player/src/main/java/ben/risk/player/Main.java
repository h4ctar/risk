package ben.risk.player;

import ben.risk.player.time.Clock;
import ben.irs.client.ClientNames;
import ben.mom.client.MomClient;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Main class.
 */
public final class Main {

    /**
     * The player window.
     */
    private PlayerWindow playerWindow;

    private MomClient momClient;

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
     * @throws IOException the message broker failed to connect to the simulator
     */
    public void start(String[] args) throws IOException {
        Options options = new Options();
        options.addOption("a", true, "Address");
        options.addOption("p", true, "Port");
        String address;
        int port;
        try {
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);
            address = cmd.getOptionValue("a");
            port = Integer.parseInt(cmd.getOptionValue("p"));
        }
        catch (@NotNull NumberFormatException|ParseException e) {
            System.err.println("Wrong arguments");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("player", options);
            return;
        }

        momClient = new MomClient(ClientNames.PLAYER_1, address, port);
        playerWindow = new PlayerWindow();

        // Start the Clock.
        new Clock(momClient);
    }

    /**
     * Stop the application.
     */
    public void stop() {
        playerWindow.stop();
        momClient.stop();
    }
}
