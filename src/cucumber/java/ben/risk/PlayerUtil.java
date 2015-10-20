package ben.risk;

import ben.ui.math.Vec2i;
import ben.risk.player.PlayerWindow;
import ben.ui.widget.IPane;
import ben.ui.widget.IWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Player Util.
 */
public final class PlayerUtil {

    private PlayerUtil() { }

    public static void clickWidget(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath) throws NotFoundException, AWTException {
        playerWindow.requestFocus();
        Robot robot = new Robot();
        robot.setAutoDelay(100);

        Vec2i pos = getWidgetPos(playerWindow, widgetPath);

        robot.mouseMove(pos.getX(), pos.getY());
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public static void typeIntoField(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath, @NotNull String text) throws NotFoundException, AWTException {
        playerWindow.requestFocus();
        Robot robot = new Robot();
        robot.setAutoDelay(100);

        Vec2i pos = getWidgetPos(playerWindow, widgetPath);

        // Select the field.
        robot.mouseMove(pos.getX(), pos.getY());
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        for (int i = 0; i < 10; i++ ) {
            robot.keyPress(KeyEvent.VK_DELETE);
            robot.keyRelease(KeyEvent.VK_DELETE);
        }

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            robot.keyPress(Character.toUpperCase(c));
            robot.keyRelease(Character.toUpperCase(c));

            if (Character.isUpperCase(c)) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }

    @NotNull
    public static Vec2i getWidgetPos(@NotNull PlayerWindow playerWindow, @NotNull String[] widgetPath) throws NotFoundException {
        IPane pane = (IPane) playerWindow.getRootWidget();

        Vec2i pos = playerWindow.getPosition();
        pos = pos.add(pane.getPosition());

        List<IWidget> widgets = findWidget(pane, widgetPath);

        for (IWidget widget : widgets) {
            pos = pos.add(widget.getPosition());
        }

        IWidget lastWidget = widgets.get(widgets.size() - 1);
        pos = pos.add(new Vec2i(lastWidget.getSize().getX() / 2, lastWidget.getSize().getY() / 2));

        return pos;
    }

    @NotNull
    public static List<IWidget> findWidget(@NotNull IPane pane, @NotNull String[] widgetPath) throws NotFoundException {
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
    public static List<IWidget> findWidget(@NotNull IPane pane, @NotNull String widgetName) throws NotFoundException {
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
