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
import java.util.Arrays;
import java.util.List;

/**
 * Player Util.
 */
public final class PlayerUtil {

    private PlayerUtil() { }

    public static void clickWidget(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath) throws NotFoundException, AWTException {
        playerWindow.requestFocus();

        Vec2i pos = getWidgetPos(playerWindow, widgetPath);

        clickMouse(playerWindow, MouseEvent.BUTTON1, pos);
    }

    public static void typeIntoField(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath, @NotNull String text) throws NotFoundException, AWTException {
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

    private static void typeKey(@NotNull PlayerWindow playerWindow, short keyCode, char keyChar) {
        pressKey(playerWindow, keyCode, keyChar);
        releaseKey(playerWindow, keyCode, keyChar);
    }

    private static void pressKey(@NotNull PlayerWindow playerWindow, short keyCode, char keyChar) {
        playerWindow.getKeyListener().keyPressed(KeyEvent.create(KeyEvent.EVENT_KEY_PRESSED, playerWindow, (long) 0, 0, keyCode, keyCode, keyChar));
    }

    private static void releaseKey(@NotNull PlayerWindow playerWindow, short keyCode, char keyChar) {
        playerWindow.getKeyListener().keyReleased(KeyEvent.create(KeyEvent.EVENT_KEY_RELEASED, playerWindow, (long) 0, 0, keyCode, keyCode, keyChar));
    }

    private static void clickMouse(@NotNull PlayerWindow playerWindow, short button, @NotNull Vec2i pos) {
        playerWindow.getMouseListener().mouseClicked(new MouseEvent((short) 0, playerWindow, 0, 0, pos.getX(), pos.getY(), (short) 0, button, null, 0));
    }

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

    @Nullable
    private static List<IWidget> findWidget(@NotNull IPane pane, @NotNull String widgetName) throws NotFoundException {
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

    public static class NotFoundException extends Exception {

        public NotFoundException(String widgetName) {
            super("Could not find " + widgetName);
        }
    }
}
