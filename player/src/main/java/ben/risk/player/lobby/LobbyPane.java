package ben.risk.player.lobby;

import ben.mom.client.IMessageProcessor;
import ben.mom.client.MomClient;
import ben.risk.irs.client.ClientNames;
import ben.risk.irs.player.PlayerRecord;
import ben.risk.irs.player.PlayerUpdated;
import ben.risk.irs.record.RequestAllRecords;
import ben.ui.widget.*;
import org.jetbrains.annotations.NotNull;

/**
 * Lobby Pane.
 */
public class LobbyPane {

    private static final int NUMBER_OF_PLAYERS = 6;
    
    private CenterPane lobbyPane = new CenterPane(null);

    private Label[] playerNameCells = new Label[NUMBER_OF_PLAYERS];

    private Label[] playerReadyCells = new Label[NUMBER_OF_PLAYERS];

    public LobbyPane(@NotNull String playerName, @NotNull MomClient momClient) {
        VerticalPane centerPane = new VerticalPane("LOBBY_PANE");
        
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            HorizontalPane playerRow = new HorizontalPane(null);

            Label playerNumberCell = new Label("PLAYER_" + i + "_NUMBER", Integer.toString(i));
            playerRow.add(playerNumberCell);

            Label playerNameCell = new Label("PLAYER_" + i + "_NAME", "                     ");
            playerNameCells[i] = playerNameCell;
            playerRow.add(playerNameCell);

            Label playerReadyCell = new Label("PLAYER_" + i + "_READY", "           ");
            playerReadyCells[i] = playerReadyCell;
            playerRow.add(playerReadyCell);

            centerPane.add(playerRow);
        }
        
        Button readyButton = new Button("READY_BUTTON", "Ready");
        readyButton.setAction(new ReadyAction(momClient, playerName));
        centerPane.add(readyButton);

        lobbyPane.setCenter(centerPane);

        momClient.subscribe(PlayerUpdated.class, new PlayerUpdatedProcessor());
        momClient.sendMessage(ClientNames.DATA_STORE, new RequestAllRecords(PlayerRecord.class));
    }

    public IWidget getPane() {
        return lobbyPane;
    }

    private class PlayerUpdatedProcessor implements IMessageProcessor<PlayerUpdated> {

        @Override
        public void processMessage(@NotNull PlayerUpdated playerUpdated) {
            PlayerRecord playerRecord = playerUpdated.getRecord();
            playerNameCells[playerRecord.getRecordId()].setText(playerRecord.getPlayerName());
            playerReadyCells[playerRecord.getRecordId()].setText(playerRecord.isReady() ? "Ready" : "Not Ready");
        }
    }
}
