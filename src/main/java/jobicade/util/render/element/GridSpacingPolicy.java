package jobicade.util.render.element;

import jobicade.util.geom.Point;

public interface GridSpacingPolicy {
    Point getPitch(Point cellSize);
}
