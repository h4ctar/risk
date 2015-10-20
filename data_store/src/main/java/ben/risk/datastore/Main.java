package ben.risk.datastore;

import ben.risk.irs.client.ClientNames;
import ben.risk.irs.game.GameDeleted;
import ben.risk.irs.game.GameUpdated;
import ben.risk.irs.game.GameRecord;
import ben.mom.client.MomClient;
import ben.risk.irs.player.PlayerDeleted;
import ben.risk.irs.player.PlayerRecord;
import ben.risk.irs.player.PlayerUpdated;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Main {

    @Nullable
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

        momClient = new MomClient(ClientNames.DATA_STORE, address, port, null);
        dataStore = new DataStore(momClient);

        dataStore.addRecordType(GameRecord.class, GameUpdated.class, GameDeleted.class);
        dataStore.addRecordType(PlayerRecord.class, PlayerUpdated.class, PlayerDeleted.class);
    }

    public void stop() {
        if (momClient != null) {
            momClient.stop();
            momClient = null;
        }
    }

    public DataStore getDataStore() {
        return dataStore;
    }
}
