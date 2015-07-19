package ben.risk.player.mvc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract View Manager.
 * <p>
 * A view manager is responsible for creating views for models.
 * @param <T> the type of model that the manager is creating views for
 */
public abstract class AbstractViewManager<T extends IModel> {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AbstractViewManager.class.getSimpleName());

    /**
     * The views that have been created.
     */
    private final Map<T, IView<T>> views = new HashMap<>();

    /**
     * The model manager that this view manager is creating views for.
     */
    @NotNull
    private final ModelManager<?, T> modelManager;

    /**
     * Constructor.
     * @param modelManager the Model Manager for this view manager
     */
    public AbstractViewManager(@NotNull ModelManager<?, T> modelManager) {
        this.modelManager = modelManager;
    }

    /**
     * Start the view manager.
     *
     * The view manager must not be added as a listener to the model manager in the constructor because the concrete
     * view managers will be asked to create views before they are properly initialised.
     */
    public void start() {
        modelManager.addListener(new ModelManagerListener());
    }

    /**
     * Create a view.
     * @param model the Model for the view
     * @return the new view
     */
    @NotNull
    public abstract IView<T> createView(@NotNull T model);

    /**
     * Model Manager Listener.
     * <p>
     * Creates a new view for each model that is added to the model manager.
     */
    private class ModelManagerListener implements IModelManagerListener<T> {

        @Override
        public void modelAdded(@NotNull T model) {
            assert !views.containsKey(model);
            IView<T> view = createView(model);
            LOGGER.info("Adding view " + view + " of " + model);
            views.put(model, view);
        }

        @Override
        public void modelRemoved(@NotNull T model) {
            assert views.containsKey(model);
            IView<T> view = views.remove(model);
            LOGGER.info("Removing view " + view + " of " + model);
            view.remove();
        }
    }
}
