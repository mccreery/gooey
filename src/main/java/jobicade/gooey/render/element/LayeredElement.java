package jobicade.gooey.render.element;

import java.util.List;

import com.google.common.collect.ImmutableList;

import jobicade.gooey.geom.Direction;
import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

public class LayeredElement extends GuiElement {
    private final List<GuiElement> source;
    private final Direction alignment;

    public LayeredElement(List<GuiElement> source, Direction alignment) {
        super(getMaxSize(source));
        this.source = ImmutableList.copyOf(source);
        this.alignment = alignment;
    }

    private static Point getMaxSize(List<GuiElement> source) {
        int x = 0, y = 0;

        for(GuiElement element : source) {
            Point size = element.getSize();
            x = Math.max(x, size.getX());
            y = Math.max(y, size.getY());
        }
        return new Point(x, y);
    }

    @Override
    public void render() {
        Rect bounds = getBounds();

        for(GuiElement element : source) {
            element.setPosition(element.getBounds()
                .anchor(bounds, alignment, false).getPosition());

            element.render();
        }
    }
}
