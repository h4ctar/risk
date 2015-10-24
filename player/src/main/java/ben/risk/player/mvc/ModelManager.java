package ben.risk.player.mvc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Model Manager.
 * <p>
 *     Manages models.
 * </p>
 * @param <K> the type to key the model by
 * @param <T> the type of model to manage
 */
public class ModelManager<K, T extends IModel> {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ModelManager.class.getSimpleName());

    /**
     * The models.
     */
    private final Map<K, T> models = new HashMap<>();

    /**
     * Listeners to be added when a model is added or removed.
     */
    private final Set<IModelManagerListener<T>> listeners = new HashSet<>();

    /**
     * Get a model.
     * @param key the key of the model to get
     * @return the model, null if the model was not found
     */
    protected final @Nullable T getModel(@NotNull K key) {
        return models.get(key);
    }

    /**
     * Add a model.
     * @param key the key for the model
     * @param model the model to add
     */
    protected final void addModel(@NotNull K key, @NotNull T model) {
        LOGGER.info("Adding model " + key + " -> " + model);
        assert !models.containsKey(key);
        models.put(key, model);
        notifyModelAdded(model);
    }

    /**
     * Remove a model.
     * @param key the key of the model to remove
     */
    protected final void removeModel(@NotNull K key) {
        LOGGER.info("Removing model " + key);
        assert models.containsKey(key);
        T model = models.remove(key);
        notifyModelRemoved(model);
    }

    /**
     * Add a listener to be notified whenever a model is added or removed.
     * @param listener the listener to add
     */
    public final void addListener(@NotNull IModelManagerListener<T> listener) {
        assert !listeners.contains(listener);
        listeners.add(listener);
        for (T model : models.values()) {
            listener.modelAdded(model);
        }
    }

    /**
     * Notify the listeners that a model has been added.
     * @param model the model that has been added
     */
    private void notifyModelAdded(@NotNull T model) {
        for (IModelManagerListener<T> listener : listeners) {
            listener.modelAdded(model);
        }
    }

    /**
     * Notify the listeners that a model has been removed.
     * @param model the model that was removed
     */
    private void notifyModelRemoved(@NotNull T model) {
        for (IModelManagerListener<T> listener : listeners) {
            listener.modelRemoved(model);
        }
    }
}
