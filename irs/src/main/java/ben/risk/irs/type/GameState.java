package ben.risk.irs.type;

import java.io.Serializable;

/**
 * Game State Name.
 */
public enum GameState implements Serializable {

    /**
     * The lobby.
     */
    LOBBY,

    /**
     * Trading.
     */
    TRADING,

    /**
     * Placing.
     */
    PLACING,

    /**
     * Attacking.
     */
    ATTACKING,

    /**
     * Fortifying.
     */
    FORTIFYING
}
