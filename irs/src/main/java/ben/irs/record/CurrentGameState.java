package ben.irs.record;

import ben.irs.type.GameState;

import java.io.Serializable;

/**
 * Current Game State.
 */
public class CurrentGameState implements Serializable {

    private final GameState gameState;

    public CurrentGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
