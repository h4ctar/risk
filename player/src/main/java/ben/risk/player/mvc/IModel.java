package ben.risk.player.mvc;

/**
 * Model Interface.
 */
public interface IModel {

    /**
     * Add a model listener.
     * @param modelListener the Model Listener to add
     */
    void addModelListener(IModelListener modelListener);

    /**
     * Remove a model listener.
     * @param modelListener the Model Listener to remove
     */
    void removeModelListener(IModelListener modelListener);
}
