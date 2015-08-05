package ben.risk.player.login;

import ben.risk.player.JoinLobbyAction;
import ben.ui.action.ActionManager;
import ben.ui.converter.IntegerConverter;
import ben.ui.converter.StringConverter;
import ben.ui.widget.*;

/**
 * Login Pane.
 */
public class LoginPane {

    private CenterPane pane = new CenterPane("LOGIN_PANE");

    public LoginPane(ActionManager actionManager) {
        VerticalPane centerPane = new VerticalPane(null);

        JoinLobbyAction joinLobbyAction = actionManager.getAction(JoinLobbyAction.class);

        HorizontalPane serverIpPane = new HorizontalPane(null);
        serverIpPane.add(new Label(null, "  Server IP:"));
        TextField<String> serverIpField = new TextField<>("SERVER_IP", StringConverter.STRING_CONVERTER);
        serverIpField.setValue("127.0.0.1");
        serverIpField.addValueListener(joinLobbyAction::setAddress);
        serverIpPane.add(serverIpField);
        centerPane.add(serverIpPane);

        HorizontalPane serverPortPane = new HorizontalPane(null);
        serverPortPane.add(new Label(null, "Server Port:"));
        TextField<Integer> serverPortField = new TextField<>("SERVER_PORT", IntegerConverter.INT_CONVERTER);
        serverPortField.setValue(1234);
        serverPortField.addValueListener(joinLobbyAction::setPort);
        serverPortPane.add(serverPortField);
        centerPane.add(serverPortPane);

        HorizontalPane playerNamePane = new HorizontalPane(null);
        playerNamePane.add(new Label(null, "Player Name:"));
        TextField<String> playerNameField = new TextField<>("PLAYER_NAME", StringConverter.STRING_CONVERTER);
        playerNameField.setValue("Ben");
        playerNameField.addValueListener(joinLobbyAction::setPlayerName);
        playerNamePane.add(playerNameField);
        centerPane.add(playerNamePane);

        Button joinButton = new Button("JOIN_BUTTON", "Join");
        joinButton.setAction(joinLobbyAction);
        centerPane.add(joinButton);

        pane.setCenter(centerPane);
    }

    public IWidget getPane() {
        return pane;
    }
}
