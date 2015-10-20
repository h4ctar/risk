package ben.risk.player.game.map.view;

import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import ben.ui.graphic.AbstractGraphic;
import ben.ui.renderer.LineRenderer;
import ben.ui.math.Vec3f;
import ben.ui.math.Geo;
import ben.ui.math.PmvMatrix;
import ben.ui.resource.GlResourceManager;
import ben.ui.resource.color.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Map Graphic Object.
 */
public class MapGraphic extends AbstractGraphic {

    /**
     * The colour to draw the map.
     */
    private static final Color COLOR = new Color(0.63f, 1.0f, 0.65f, 0.7f);

    /**
     * The map.
     */
    @NotNull
    private final List<Geo> map;

    /**
     * The line renderer.
     */
    private LineRenderer lineRenderer;

    /**
     * Constructor.
     * @param map the map
     */
    public MapGraphic(@NotNull List<Geo> map) {
        this.map = map;
    }

    @Override
    protected final void initDraw(@NotNull GL2 gl, @NotNull GlResourceManager glResourceManager) {
        float[] positions = createPositions();
        lineRenderer = new LineRenderer(gl, glResourceManager, positions, 3, GL.GL_LINE_LOOP, COLOR);
    }

    @Override
    protected final void updateDraw(@NotNull GL2 gl) { }

    @Override
    protected final void doDraw(@NotNull GL2 gl, @NotNull PmvMatrix pmvMatrix) {
        lineRenderer.draw(gl, pmvMatrix);
    }

    /**
     * Create the positions array.
     * @return the positions
     */
    @NotNull
    private float[] createPositions() {
        float[] positions = new float[map.size() * 3];
        for (int i = 0; i < map.size(); i++) {
            Vec3f position = map.get(i).getCartesian();
            positions[i * 3] = position.getX();
            positions[i * 3 + 1] = position.getY();
            positions[i * 3 + 2] = position.getZ();
        }
        return positions;
    }

    @Override
    public void remove(@NotNull GL2 gl) {
        super.remove(gl);
        lineRenderer.remove(gl);
    }
}
