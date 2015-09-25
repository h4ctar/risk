package ben.risk.master;

/**
 * Player.
 */
public class Player {

    private final int playerNumber;

    private final String name;

    private boolean ready;

    public Player(int playerNumber, String name) {
        this.playerNumber = playerNumber;
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getPlayerName() {
        return name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
