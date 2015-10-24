package ben.risk;

import ben.ui.math.Vec2i;
import ben.risk.player.PlayerWindow;
import ben.ui.widget.IPane;
import ben.ui.widget.IWidget;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.KeyEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Player Util.
 */
public final class PlayerUtil {

    /**
     * Private Constructor.
     */
    private PlayerUtil() { }

    /**
     * Click on a widget.
     * @param playerWindow the player window
     * @param widgetPath the widget path
     * @throws NotFoundException the widget could not be found
     */
    public static void clickWidget(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath) throws NotFoundException {
        playerWindow.requestFocus();

        Vec2i pos = getWidgetPos(playerWindow, widgetPath);

        clickMouse(playerWindow, MouseEvent.BUTTON1, pos);
    }

    /**
     * Type text into a field
     * @param playerWindow the player window
     * @param widgetPath the widget path
     * @param text the text to type into the field
     * @throws NotFoundException the widget could not be found
     */
    public static void typeIntoField(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath, @NotNull String text) throws NotFoundException {
        playerWindow.requestFocus();

        Vec2i pos = getWidgetPos(playerWindow, widgetPath);

        // Select the field.
        clickMouse(playerWindow, MouseEvent.BUTTON1, pos);

        for (int i = 0; i < 10; i++ ) {
            typeKey(playerWindow, KeyEvent.VK_DELETE, (char) 0);
        }

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                pressKey(playerWindow, KeyEvent.VK_SHIFT, (char) 0);
            }

            typeKey(playerWindow, KeyEvent.utf16ToVKey(Character.toUpperCase(c)), c);

            if (Character.isUpperCase(c)) {
                releaseKey(playerWindow, KeyEvent.VK_SHIFT, (char) 0);
            }
        }
    }

    /**
     * Type a key.
     * @param playerWindow the player window
     * @param keyCode the key code
     * @param keyChar the key char
     */
    private static void typeKey(@NotNull PlayerWindow playerWindow, short keyCode, char keyChar) {
        pressKey(playerWindow, keyCode, keyChar);
        releaseKey(playerWindow, keyCode, keyChar);
    }

    /**
     * Press a key.
     * @param playerWindow the player window
     * @param keyCode the key code
     * @param keyChar the key char
     */
    private static void pressKey(@NotNull PlayerWindow playerWindow, short keyCode, char keyChar) {
        playerWindow.getKeyListener().keyPressed(KeyEvent.create(KeyEvent.EVENT_KEY_PRESSED, playerWindow, (long) 0, 0, keyCode, keyCode, keyChar));
    }

    /**
     * Release a key.
     * @param playerWindow the player window
     * @param keyCode the key code
     * @param keyChar the key char
     */
    private static void releaseKey(@NotNull PlayerWindow playerWindow, short keyCode, char keyChar) {
        playerWindow.getKeyListener().keyReleased(KeyEvent.create(KeyEvent.EVENT_KEY_RELEASED, playerWindow, (long) 0, 0, keyCode, keyCode, keyChar));
    }

    /**
     * Click the mouse.
     * @param playerWindow the player window
     * @param button the button
     * @param pos the position to click
     */
    private static void clickMouse(@NotNull PlayerWindow playerWindow, short button, @NotNull Vec2i pos) {
        playerWindow.getMouseListener().mouseMoved(new MouseEvent((short) 0, playerWindow, 0, 0, pos.getX(), pos.getY(), (short) 0, button, null, 0));
        playerWindow.getMouseListener().mouseClicked(new MouseEvent((short) 0, playerWindow, 0, 0, pos.getX(), pos.getY(), (short) 0, button, null, 0));
    }

    /**
     * Get the position of a widget.
     * @param playerWindow the player window
     * @param widgetPath the widget path
     * @return the widget position
     * @throws NotFoundException the widget could not be found
     */
    @NotNull
    private static Vec2i getWidgetPos(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath) throws NotFoundException {
        IPane pane = (IPane) playerWindow.getRootWidget();

        if (pane == null) {
            throw new NotFoundException("Root widget");
        }

        Vec2i pos = pane.getPosition();

        List<IWidget> widgets = findWidget(pane, widgetPath);

        for (IWidget widget : widgets) {
            pos = pos.add(widget.getPosition());
        }

        IWidget lastWidget = widgets.get(widgets.size() - 1);
        pos = pos.add(new Vec2i(lastWidget.getSize().getX() / 2, lastWidget.getSize().getY() / 2));

        return pos;
    }

    /**
     * Find a widget.
     * @param pane the pane to find the widget in
     * @param widgetPath the widget path
     * @return the widget
     * @throws NotFoundException the widget could not be found
     */
    @NotNull
    private static List<IWidget> findWidget(@NotNull IPane pane, @NotNull String[] widgetPath) throws NotFoundException {
        List<IWidget> widgets = new ArrayList<>();

        for (int i = 0; i < widgetPath.length - 1; i++) {
            String paneName = widgetPath[i];
            widgets = findWidget(pane, paneName);
            if (widgets == null) {
                throw new NotFoundException(paneName);
            }

            IWidget lastWidget = widgets.get(widgets.size() - 1);
            if (lastWidget instanceof IPane) {
                pane = (IPane) lastWidget;
            }
            else {
                throw new NotFoundException(paneName);
            }
        }

        String widgetName = widgetPath[widgetPath.length - 1];
        List<IWidget> widgets2 = findWidget(pane, widgetName);
        if (widgets2 == null) {
            throw new NotFoundException(widgetName);
        }
        widgets.addAll(widgets2);

        return widgets;
    }

    /**
     * Find a widget.
     * @param pane the pane to find the widget in
     * @param widgetName the name of the widget to find
     * @return all the widgets from the pane to the found widget, null if the widget could not be found
     */
    private static List<IWidget> findWidget(@NotNull IPane pane, @NotNull String widgetName) {
        List<IWidget> widgets = null;

        for (IWidget widget : pane.getWidgets()) {
            if (widget.getName() == null && widget instanceof IPane) {
                widgets = findWidget((IPane) widget, widgetName);
                if (widgets != null) {
                    widgets.add(0, widget);
                    break;
                }
            }
            else if (widgetName.equals(widget.getName())) {
                widgets = new ArrayList<>();
                widgets.add(widget);
                break;
            }
        }

        return widgets;
    }

    /**
     * Not Found Exception.
     */
    public static class NotFoundException extends Exception {

        /**
         * Constructor.
         * @param widgetName the name of the widget that could not be found
         */
        public NotFoundException(String widgetName) {
            super("Could not find " + widgetName);
        }
    }
}
