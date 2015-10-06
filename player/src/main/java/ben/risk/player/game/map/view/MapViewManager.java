package ben.risk.player.game.map.view;

import ben.risk.player.game.WorldCanvas;
import ben.risk.player.game.map.model.MapModel;
import ben.risk.player.mvc.AbstractViewManager;
import ben.risk.player.mvc.IView;
import ben.risk.player.mvc.ModelManager;
import org.jetbrains.annotations.NotNull;

/**
 * Map Controller.
 */
public class MapViewManager extends AbstractViewManager<MapModel> {

    @NotNull
    private final WorldCanvas worldCanvas;

    /**
     * Constructor.
     * @param modelManager the model manager
     * @param worldCanvas the world canvas
     */
    public MapViewManager(@NotNull ModelManager<?, MapModel> modelManager, @NotNull WorldCanvas worldCanvas) {
        super(modelManager);
        this.worldCanvas = worldCanvas;
    }

    @Override
    @NotNull
    public final IView<MapModel> createView(@NotNull MapModel model) {
        return new MapView(model, worldCanvas);
    }
}
