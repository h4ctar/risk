package ben.risk.irs.type;

import java.io.Serializable;

/**
 * Game State Name.
 */
public enum GameState implements Serializable {
    LOBBY,
    TRADING,
    PLACING,
    ATTACKING,
    FORTIFYING
}
