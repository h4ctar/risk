package ben.risk.player.lobby;

import ben.mom.client.IMomClient;
import ben.risk.irs.Ready;
import ben.risk.irs.client.ClientNames;
import ben.ui.action.AbstractAction;
import org.jetbrains.annotations.NotNull;

/**
 * Ready Action.
 */
public final class ReadyAction extends AbstractAction {

    /**
     * The MOM client.
     */
    @NotNull
    private final IMomClient momClient;

    /**
     * The player name.
     */
    @NotNull
    private final String playerName;

    /**
     * Constructor.
     * @param momClient the MOM client
     * @param playerName the player name
     */
    public ReadyAction(@NotNull IMomClient momClient, @NotNull String playerName) {
        this.momClient = momClient;
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return ReadyAction.class.getSimpleName() + "[playerName: " + playerName + "]";
    }

    @Override
    protected void doAction() {
        momClient.sendMessage(ClientNames.MASTER, new Ready(playerName));
    }
}
