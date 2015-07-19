package ben.risk.player;

import ben.ui.widget.*;
import ben.ui.window.MainWindow;
import org.jetbrains.annotations.NotNull;

/**
 * Player Window.
 * <p>
 *      The main window for the player client.
 * </p>
 */
public final class PlayerWindow extends MainWindow {

    /**
     * The Root Pane.
     */
    @NotNull
    private final BorderPane rootPane;

    /**
     * Constructor.
     */
    public PlayerWindow() {
        rootPane = new BorderPane("ROOT");
        setRootWidget(rootPane);
    }

    /**
     * Get the root pane.
     * @return the root pane
     */
    @NotNull
    public BorderPane getRootPane() {
        return rootPane;
    }
}
