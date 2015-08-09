package ben.risk.master;

/**
 * Game State Name.
 */
public enum GameStateName {
    LOBBY(ben.risk.irs.type.GameStateName.LOBBY),
    TRADING(ben.risk.irs.type.GameStateName.TRADING),
    PLACING(ben.risk.irs.type.GameStateName.PLACING),
    ATTACKING(ben.risk.irs.type.GameStateName.ATTACKING),
    FORTIFYING(ben.risk.irs.type.GameStateName.FORTIFYING);

    private final ben.risk.irs.type.GameStateName irsGameStateName;

    GameStateName(ben.risk.irs.type.GameStateName irsGameStateName) {
        this.irsGameStateName = irsGameStateName;
    }

    public ben.risk.irs.type.GameStateName getIrsGameStateName() {
        return irsGameStateName;
    }
}
