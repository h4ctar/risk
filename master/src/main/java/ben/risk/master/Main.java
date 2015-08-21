package ben.risk.master;

import java.io.IOException;

import ben.risk.irs.client.ClientNames;
import ben.mom.client.MomClient;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Main {

    @Nullable
    private MomClient momClient;

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
            formatter.printHelp("master", options);
            return;
        }

        momClient = new MomClient(ClientNames.MASTER, address, port, null);
        new Master(momClient);
    }

    public void stop() {
        if (momClient != null) {
            momClient.stop();
            momClient = null;
        }
    }
}
