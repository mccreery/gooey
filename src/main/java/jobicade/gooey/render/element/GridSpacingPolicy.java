package jobicade.gooey.render.element;

import jobicade.gooey.geom.Point;

public interface GridSpacingPolicy {
    Point getPitch(Point cellSize);
}
