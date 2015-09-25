package ben.risk.irs.player;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Player Updated.
 */
@Immutable
public final class PlayerUpdated implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    private final PlayerRecord record;

    public PlayerUpdated(@NotNull PlayerRecord record) {
        this.record = record;
    }

    @NotNull
    public PlayerRecord getRecord() {
        return record;
    }
}
