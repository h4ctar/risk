package ben.risk.player.lobby;

import ben.ui.widget.Button;
import ben.ui.widget.CenterPane;
import ben.ui.widget.IWidget;

/**
 * Lobby Pane.
 */
public class LobbyPane {

    private CenterPane pane = new CenterPane("LOBBY_PANE");

    public LobbyPane() {
        Button readyButton = new Button("READY", "Ready");
        pane.setCenter(readyButton);
    }

    public IWidget getPane() {
        return pane;
    }
}
