package ben.risk.player.login;

import ben.risk.player.IPlayerWindow;
import ben.ui.converter.IntegerConverter;
import ben.ui.converter.StringConverter;
import ben.ui.widget.*;
import org.jetbrains.annotations.NotNull;

/**
 * Login Pane.
 */
public final class LoginPane {

    /**
     * The widget for the pane.
     */
    @NotNull
    private CenterPane loginPane = new CenterPane(null);

    /**
     * Constructor.
     * @param playerWindow the player window
     */
    public LoginPane(@NotNull IPlayerWindow playerWindow) {
        VerticalPane centerPane = new VerticalPane("LOGIN_PANE");

        final JoinLobbyAction joinLobbyAction = new JoinLobbyAction(playerWindow);

        HorizontalPane serverIpPane = new HorizontalPane(null);
        serverIpPane.add(new Label(null, "  Server IP:"));
        TextField<String> serverIpField = new TextField<>("SERVER_IP", StringConverter.STRING_CONVERTER);
        serverIpField.setValue("127.0.0.1");
        serverIpField.addValueListener(new IValueListener<String>() {

            @Override
            public void valueChanged(String value) {
                joinLobbyAction.setAddress(value);
            }
        });
        serverIpPane.add(serverIpField);
        centerPane.add(serverIpPane);

        HorizontalPane serverPortPane = new HorizontalPane(null);
        serverPortPane.add(new Label(null, "Server Port:"));
        TextField<Integer> serverPortField = new TextField<>("SERVER_PORT", IntegerConverter.INT_CONVERTER);
        serverPortField.setValue(1234);
        serverPortField.addValueListener(new IValueListener<Integer>() {

            @Override
            public void valueChanged(Integer value) {
                joinLobbyAction.setPort(value);
            }
        });
        serverPortPane.add(serverPortField);
        centerPane.add(serverPortPane);

        HorizontalPane playerNamePane = new HorizontalPane(null);
        playerNamePane.add(new Label(null, "Player Name:"));
        TextField<String> playerNameField = new TextField<>("PLAYER_NAME", StringConverter.STRING_CONVERTER);
        playerNameField.setValue("Ben");
        playerNameField.addValueListener(new IValueListener<String>() {
            @Override
            public void valueChanged(String value) {
                joinLobbyAction.setPlayerName(value);
            }
        });
        playerNamePane.add(playerNameField);
        centerPane.add(playerNamePane);

        Button joinButton = new Button("JOIN_BUTTON", "Join");
        joinButton.setAction(joinLobbyAction);
        centerPane.add(joinButton);

        loginPane.setCenter(centerPane);
    }

    /**
     * Get the pane.
     * @return the pane
     */
    public IWidget getPane() {
        return loginPane;
    }
}
