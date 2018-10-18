package jobicade.util.render.element;

import java.util.List;

import com.google.common.collect.ImmutableList;

import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;
import jobicade.util.geom.Rectangle;

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
        Rectangle bounds = getBounds();

        for(GuiElement element : source) {
            element.setPosition(element.getBounds()
                .anchor(bounds, alignment, false).getPosition());

            element.render();
        }
    }
}
