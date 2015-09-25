package ben.risk.master;

import ben.risk.irs.type.GameState;

/**
 * Game State Name.
 */
public enum GameStateName {
    LOBBY(GameState.LOBBY),
    TRADING(GameState.TRADING),
    PLACING(GameState.PLACING),
    ATTACKING(GameState.ATTACKING),
    FORTIFYING(GameState.FORTIFYING);

    private final GameState irsGameState;

    GameStateName(GameState irsGameState) {
        this.irsGameState = irsGameState;
    }

    public GameState getIrsGameState() {
        return irsGameState;
    }
}
