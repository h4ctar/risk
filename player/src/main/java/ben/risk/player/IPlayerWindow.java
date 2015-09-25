package ben.risk.player;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Player Window Interface.
 */
public interface IPlayerWindow {

    /**
     * Join a game lobby.
     * @param address the address of the MOM Server
     * @param port the port of the MOM Server
     * @param playerName the name of the player
     */
    void joinLobby(@NotNull String address, int port, @NotNull String playerName) throws IOException;
}
