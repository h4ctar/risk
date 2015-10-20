package ben.risk.momserver;

import ben.mom.server.MomServer;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * The main entry point for the MOM Server application.
 */
public final class Main {

    @Nullable
    private MomServer momServer;

    /**
     * Main.
     * @param args the application arguments
     * @throws IOException the Message Broker Client could not connect
     */
    public static void main(String[] args) throws IOException {
        new Main().start(args);
    }

    public void start(String[] args) throws IOException {
        Options options = new Options();
        options.addOption("p", true, "Port");
        int port;
        try {
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);
            port = Integer.parseInt(cmd.getOptionValue("p"));
        }
        catch (@NotNull NumberFormatException|ParseException e) {
            System.err.println("Wrong arguments");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("mom_server", options);
            return;
        }

        momServer = new MomServer(port, false);
    }

    public void stop() {
        if (momServer != null) {
            momServer.stop();
            momServer = null;
        }
    }
}
