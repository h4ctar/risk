package ben.momserver;

import ben.mom.server.MomServer;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * The main entry point for the MOM Server application.
 */
public final class Main {

    /**
     * Private Constructor.
     */
    private Main() { }

    /**
     * Main.
     * @param args the program arguments
     */
    public static void main(String[] args) throws IOException {
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

        new MomServer(port, false);
    }
}
