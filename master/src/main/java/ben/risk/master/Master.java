package ben.risk.master;

import ben.risk.irs.client.ClientNames;
import ben.risk.irs.game.GameRecord;
import ben.risk.irs.record.WriteRecord;
import ben.mom.client.IMomClient;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Master.
 */
public final class Master implements IGameStateController {

    @NotNull
    private final IMomClient momClient;

    @NotNull
    private final Map<GameStateName, IGameState> gameStates = new HashMap<>();

    /**
     * The name of the current game state.
     */
    @NotNull
    private GameStateName gameStateName = GameStateName.LOBBY;

    /**
     * Constructor.
     * @param momClient the MOM Client
     */
    public Master(@NotNull IMomClient momClient) {
        this.momClient = momClient;

        gameStates.put(GameStateName.LOBBY, new LobbyState(momClient));
        gameStates.put(GameStateName.TRADING, new TradingGameState());
        gameStates.put(GameStateName.PLACING, new PlacingGameState());
        gameStates.put(GameStateName.ATTACKING, new AttackingGameState());
        gameStates.put(GameStateName.FORTIFYING, new FortifyingGameState());

        writeGameState();
    }

    @Override
    public void setState(GameStateName gameStateName) {

    }

    private void writeGameState() {
        ben.risk.irs.type.GameStateName irsGameStateName = gameStateName.getIrsGameStateName();
        momClient.sendMessage(ClientNames.DATA_STORE, new WriteRecord(new GameRecord(irsGameStateName)));
    }
}
