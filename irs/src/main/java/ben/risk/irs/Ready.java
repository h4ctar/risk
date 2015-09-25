package ben.risk.irs;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Ready.
 */
@Immutable
public final class Ready implements Serializable {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    private final String playerName;

    public Ready(@NotNull String playerName) {
        this.playerName = playerName;
    }

    @NotNull
    public String getPlayerName() {
        return playerName;
    }
}
