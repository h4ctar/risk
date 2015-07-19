package ben.risk.player.mvc;

/**
 * View Interface.
 * @param <T> the type of model that the view is for
 */
public interface IView<T extends IModel> {

    /**
     * The view has been removed and needs to remove its Graph Objects from the Viewport.
     */
    void remove();
}
