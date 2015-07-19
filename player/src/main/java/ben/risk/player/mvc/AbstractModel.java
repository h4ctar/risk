package ben.risk.player.mvc;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract Model.
 * <p>
 *     Provides the model listener functionality.
 * </p>
 */
public abstract class AbstractModel implements IModel {

    /**
     * The model listeners.
     */
    private final Set<IModelListener> modelListeners = new HashSet<>();

    @Override
    public final void addModelListener(@NotNull IModelListener modelListener) {
        assert !modelListeners.contains(modelListener);
        modelListeners.add(modelListener);
        modelListener.modelChanged();
    }

    @Override
    public final void removeModelListener(@NotNull IModelListener modelListener) {
        assert modelListeners.contains(modelListener);
        modelListeners.remove(modelListener);
    }


    /**
     * Notify all the Model Listeners that the model has changed.
     */
    protected final void notifyModelListeners() {
        for (IModelListener modelListener : modelListeners) {
            modelListener.modelChanged();
        }
    }
}
