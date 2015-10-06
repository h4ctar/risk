package ben.risk.player.game;

import ben.ui.input.mouse.MouseButton;
import ben.ui.input.mouse.MouseListenerAdapter;
import ben.math.Matrix;
import ben.math.PmvMatrix;
import ben.math.Rect;
import ben.math.Vec2i;
import ben.ui.widget.Canvas;
import ben.math.Vec3f;
import ben.math.Vec4f;
import ben.math.Geo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * World Canvas.
 * <p>
 *     Draws the Graphic Objects in world space, like maps.
 *     Provides methods to get the screen coordinates of world and cartesian coordinates.
 * </p>
 */
public final class WorldCanvas extends Canvas {

    /**
     * The minimum zoom level.
     * <p>
     *     1.0f will be on the ground as the world radius is also 1.0f;
     *     Add a little bit so we don't go past the minimum draw distance.
     * </p>
     */
    private static final float MIN_ZOOM = 1.002f;

    /**
     * The maximum zoom level.
     */
    private static final float MAX_ZOOM = 5.0f;

    /**
     * The world position that is currently centered.
     */
    @NotNull
    private Geo worldRotation = new Geo(0, 0);

    /**
     * The current zoom level.
     */
    private float zoom = MAX_ZOOM;

    /**
     * Constructor.
     */
    public WorldCanvas() {
        super(null);
        getMouseHandler().addMouseListener(new MouseListener());
    }

    @NotNull
    @Override
    protected PmvMatrix getPmvMatrix() {
        Rect canvasViewport = new Rect(new Vec2i(0, 0), getSize());
        PmvMatrix pmvMatrix = new PmvMatrix();

        // Calculate the view distance.
        // http://www-rohan.sdsu.edu/~aty/explain/atmos_refr/horizon.html
        float view = zoom - 1 / zoom;

        pmvMatrix.perspective(canvasViewport, 0.001f, view);
        pmvMatrix.translate(new Vec3f(0.0f, 0.0f, -zoom));
        pmvMatrix.rotateX((float) worldRotation.getLatitude());

        // Subtract PI / 2 so that zero is zero.
        pmvMatrix.rotateY((float) worldRotation.getLongitude() - (float) Math.PI / 2);
        return pmvMatrix;
    }

    /**
     * Get the screen position of a world position.
     * <p>
     *     Returns null if the position is not visible on the screen.
     * </p>
     * @param worldPosition the world position
     * @return the screen coordinates
     */
    @Nullable
    public Vec2i getScreenPosition(@NotNull Geo worldPosition) {
        Vec3f cartesian = worldPosition.getCartesian();
        return getScreenPosition(cartesian);
    }

    /**
     * Get the screen position of a 3D position.
     * <p>
     *     Returns null if the position is not visible on the screen.
     * </p>
     * @param cartesian the 3D position in cartesian space
     * @return the screen coordinates
     */
    @Nullable
    public Vec2i getScreenPosition(@NotNull Vec3f cartesian) {
        return getPmvMatrix().project(cartesian);
    }

    /**
     * Get the world position of a screen position.
     * <p>
     *     Returns null if the position is not on the world.
     * </p>
     * @param screen the screen position
     * @return the world position
     */
    @Nullable
    public Geo getWorldPosition(@NotNull Vec2i screen) {
        Geo world = null;

        // http://www.opengl-tutorial.org/miscellaneous/clicking-on-objects/picking-with-a-physics-library/
        // http://www.lighthouse3d.com/tutorials/maths/ray-sphere-intersection/
        // http://www.lighthouse3d.com/tutorials/maths/line-and-rays/
        Vec4f rayStartNdc = new Vec4f(((float) screen.getX() / getSize().getX() - 0.5f) * 2.0f, -((float) screen.getY() / getSize().getY() - 0.5f) * 2.0f, -1.0f, 1.0f);
        Vec4f rayEndNdc = new Vec4f(rayStartNdc.getX(), rayStartNdc.getY(), 1.0f, 1.0f);

        Matrix inversePmv = getPmvMatrix().getPmvMatrix().inverse();

        if (inversePmv != null) {
            Vec4f c = new Vec4f(0.0f, 0.0f, 0.0f, 1.0f);
            float r = 1.0f;

            Vec4f p = Matrix.mul(inversePmv, rayStartNdc);
            p = p.div(p.getW());
            Vec4f rayEndWorld = Matrix.mul(inversePmv, rayEndNdc);
            rayEndWorld = rayEndWorld.div(rayEndWorld.getW());

            Vec4f d = rayEndWorld.sub(p).normalize();

            // vpc = c - p
            Vec4f vpc = c.sub(p);

            // vpc.d
            if (vpc.dot(d) >= 0) {
                // pc = projection of c on the line
                Vec4f pc = p.add(d.mul(d.dot(vpc) / d.length()));

                // |c - pc|
                if (c.sub(pc).length() <= r) {
                    // distance from pc to i1
                    // dist = sqrt(radius^2 - |pc - c|^2)
                    float dist = (float) Math.sqrt(r * r - Math.pow(pc.sub(c).length(), 2));

                    float di1;
                    // origin is outside sphere
                    if (vpc.length() > r) {
                        di1 = pc.sub(p).length() - dist;
                    }
                    else {
                        di1 = pc.sub(p).length() + dist;
                    }

                    Vec4f intersection = p.add(d.mul(di1));

                    world = new Geo(new Vec3f(intersection));
                }
            }
        }

        return world;
    }

    /**
     * Mouse Listener.
     * <p>
     *     Responsible for zooming and panning.
     * </p>
     */
    private class MouseListener extends MouseListenerAdapter {

        /**
         * The position of the mouse when the pan starts, null if not panning.
         */
        @Nullable
        private Vec2i startMousePosition;

        /**
         * The world rotate when the pan starts, null if not panning.
         */
        @Nullable
        private Geo startWorldRotation;

        @Override
        public void mousePressed(@NotNull MouseButton button, @NotNull Vec2i mousePosition) {
            startMousePosition = mousePosition;
            startWorldRotation = worldRotation;
        }

        @Override
        public void mouseReleased(@NotNull MouseButton button, @NotNull Vec2i mousePosition) {
            startMousePosition = null;
            startWorldRotation = null;
        }

        @Override
        public void mouseDragged(@NotNull Vec2i mousePosition) {
            if (startMousePosition != null && startWorldRotation != null) {
                float rotateFactor = (zoom - 1.0f) * 0.001f;
                worldRotation = new Geo(startWorldRotation.getLatitude() + (mousePosition.getY() - startMousePosition.getY()) * rotateFactor,
                        startWorldRotation.getLongitude() + (mousePosition.getX() - startMousePosition.getX()) * rotateFactor);
            }
        }

        @Override
        public void mouseWheelMoved(float wheel) {
            System.out.println("wheel " + wheel);
            float scaleFactor = (zoom - 1.0f) * 0.1f;
            zoom -= wheel * scaleFactor;
            if (zoom < MIN_ZOOM) {
                zoom = MIN_ZOOM;
            }
            else if (zoom > MAX_ZOOM) {
                zoom = MAX_ZOOM;
            }
        }
    }
}
