package ben.risk.player.game;

import ben.risk.player.game.map.model.MapModelManager;
import ben.risk.player.game.map.view.MapViewManager;
import ben.ui.widget.IWidget;
import ben.ui.widget.StackPane;

/**
 * Game Pane.
 */
public class GamePane {

    private StackPane gamePane;

    public GamePane() {
        gamePane = new StackPane(null);

        WorldCanvas worldCanvas = new WorldCanvas();
        gamePane.add(worldCanvas);

        gamePane.add(new WorldPane());

        MapModelManager mapModelManager = new MapModelManager();
        MapViewManager mapController = new MapViewManager(mapModelManager, worldCanvas);
        mapController.start();
    }

    public IWidget getPane() {
        return gamePane;
    }
}
