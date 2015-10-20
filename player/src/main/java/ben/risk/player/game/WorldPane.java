package ben.risk.player.game;

import ben.ui.math.PmvMatrix;
import ben.ui.resource.GlResourceManager;
import ben.ui.widget.AbstractPane;
import ben.ui.widget.IWidget;
import org.jetbrains.annotations.NotNull;

import com.jogamp.opengl.GL2;

/**
 * World Pane.
 */
public final class WorldPane extends AbstractPane {

    public WorldPane() {
        super(null);
    }

    @Override
    protected void initDraw(@NotNull GL2 gl, @NotNull GlResourceManager glResourceManager) { }

    @Override
    protected void updateDraw(@NotNull GL2 gl) { }

    @Override
    protected void doDraw(@NotNull GL2 gl, @NotNull PmvMatrix pmvMatrix) { }

    @Override
    protected void updateLayout() { }

    @Override
    public void updateSize() { }

    public void add(@NotNull IWidget widget) {
        addWidget(widget);
    }

    public void remove(@NotNull IWidget widget) {
        removeWidget(widget);
    }
}
