package ben.risk.player;

import ben.ui.action.AbstractAction;
import ben.ui.rule.AbstractRule;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Join Lobby Action.
 */
public class JoinLobbyAction extends AbstractAction {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(JoinLobbyAction.class.getSimpleName());

    @NotNull
    private final PlayerWindow playerWindow;

    @NotNull
    private final LocalRule localRule;

    @Nullable
    private String address;

    @Nullable
    private Integer port;

    @Nullable
    private String playerName;

    public JoinLobbyAction(@NotNull PlayerWindow playerWindow) {
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

    public void setAddress(@Nullable String address) {
        this.address = address;
        localRule.update();
    }

    public void setPort(@Nullable Integer port) {
        this.port = port;
        localRule.update();
    }

    public void setPlayerName(@Nullable String playerName) {
        this.playerName = playerName;
        localRule.update();
    }

    private class LocalRule extends AbstractRule {

        public void update() {
            setValid(address != null && port != null && playerName != null);
        }
    }
}
