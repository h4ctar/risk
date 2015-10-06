package ben.risk.player.game.map.model;

import ben.risk.player.mvc.ModelManager;

/**
 * Map Model Manager.
 */
public class MapModelManager extends ModelManager<String, MapModel> {

    /**
     * Constructor.
     */
    public MapModelManager() {
        addModel("Australia", new MapModel("/maps/australia.xml"));
        addModel("Africa", new MapModel("/maps/africa.xml"));
    }
}
