package ben.risk.player.mvc;

import org.jetbrains.annotations.NotNull;

/**
 * Model Manager Listener Interface.
 * @param <T> the type of model to listen for
 */
public interface IModelManagerListener<T extends IModel> {

    /**
     * A new model has been added.
     * @param model the added model
     */
    void modelAdded(@NotNull T model);

    /**
     * A new model has been removed.
     * @param model the removed model
     */
    void modelRemoved(@NotNull T model);
}
