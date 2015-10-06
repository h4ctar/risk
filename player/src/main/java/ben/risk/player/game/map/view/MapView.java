package ben.risk.player.game.map.view;

import ben.risk.player.game.WorldCanvas;
import ben.risk.player.game.map.model.MapModel;
import ben.risk.player.mvc.IView;
import org.jetbrains.annotations.NotNull;

/**
 * Map View.
 */
public final class MapView implements IView<MapModel> {

    /**
     * The world canvas.
     */
    private final WorldCanvas worldCanvas;

    /**
     * The graphic.
     */
    @NotNull
    private final MapGraphic mapGraphic;

    /**
     * Constructor.
     * @param mapModel the map model
     * @param worldCanvas the world canvas
     */
    public MapView(@NotNull MapModel mapModel, WorldCanvas worldCanvas) {
        this.worldCanvas = worldCanvas;
        mapGraphic = new MapGraphic(mapModel.getMap());
        this.worldCanvas.addGraphic(mapGraphic);
    }

    @Override
    public String toString() {
        return MapView.class.getSimpleName();
    }

    @Override
    public void remove() {
        worldCanvas.removeGraphic(mapGraphic);
    }
}
