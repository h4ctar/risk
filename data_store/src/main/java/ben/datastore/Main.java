package ben.datastore;

import ben.irs.client.ClientNames;
import ben.irs.mock.MockDeleted;
import ben.irs.mock.MockRecord;
import ben.irs.mock.MockUpdated;
import ben.mom.client.MomClient;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

public final class Main {

    private MomClient momClient;

    private DataStore dataStore;

    public static void main(String[] args) throws Exception {
        new Main().start(args);
    }

    public void start(String[] args) throws Exception {
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
            formatter.printHelp("data_store", options);
            return;
        }

        momClient = new MomClient(ClientNames.DATA_STORE, address, port);
        dataStore = new DataStore(momClient);
        dataStore.addRecordType(MockRecord.class, MockUpdated.class, MockDeleted.class);
    }

    public void stop() {
        momClient.stop();
//        dataStore.stop();
    }
}
