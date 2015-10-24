package ben.risk.player.login;

import ben.risk.player.IPlayerWindow;
import ben.ui.action.AbstractAction;
import ben.ui.rule.AbstractRule;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Join Lobby Action.
 * <p>
 *     Action that will call the joinLobby method on the player window.
 * </p>
 */
public final class JoinLobbyAction extends AbstractAction {

    /**
     * The Logger.
     */
    @NotNull
    private static final Logger LOGGER = LogManager.getLogger(JoinLobbyAction.class);

    /**
     * The player window.
     */
    @NotNull
    private final IPlayerWindow playerWindow;

    /**
     * The local rule.
     */
    @NotNull
    private final LocalRule localRule;

    /**
     * The IP address of the game master.
     */
    @Nullable
    private String address;

    /**
     * The port of the game master.
     */
    @Nullable
    private Integer port;

    /**
     * The player name.
     */
    @Nullable
    private String playerName;

    /**
     * Constructor.
     * @param playerWindow the player window
     */
    public JoinLobbyAction(@NotNull IPlayerWindow playerWindow) {
        this.playerWindow = playerWindow;
        localRule = new LocalRule();
        addRule(localRule);
    }

    @Override
    public String toString() {
        return JoinLobbyAction.class.getSimpleName() + "[address: " + address + ", port: " + port + ", playerName: " + playerName + "]";
    }

    @Override
    protected void doAction() {
        assert address != null : "Rule will stop address from being null";
        assert port != null : "Rule will stop port from being null";
        assert playerName != null : "Rule will stop player name from being null";

        try {
            playerWindow.joinLobby(address, port, playerName);
        }
        catch (IOException e) {
            LOGGER.error("Could not join the game", e);
        }
    }

    /**
     * Set the IP address of the game master.
     * @param address the IP address
     */
    public void setAddress(@Nullable String address) {
        this.address = address;
        localRule.update();
    }

    /**
     * Set the port of the game master.
     * @param port the port
     */
    public void setPort(@Nullable Integer port) {
        this.port = port;
        localRule.update();
    }

    /**
     * Set the player name.
     * @param playerName the player name
     */
    public void setPlayerName(@Nullable String playerName) {
        this.playerName = playerName;
        localRule.update();
    }

    /**
     * Local Rule.
     * <p>
     *     Stops the action being executed if any of it's fields are null.
     * </p>
     */
    private class LocalRule extends AbstractRule {

        /**
         * Update the rule.
         */
        public void update() {
            setValid(address != null && port != null && playerName != null);
        }
    }
}
