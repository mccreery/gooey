package jobicade.gooey.layout;

import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

public class Align {
    // TODO javadoc
    public static Rect alignAround(Rect rect, Point anchor, float tx, float ty) {
        Point toAlign = rect.getSize().scale(tx, ty);
        return rect.withPosition(anchor.sub(toAlign));
    }

    // TODO javadoc
    public static Rect alignInside(Rect rect, Rect container, float tx, float ty) {
        Point anchor = container.interpolate(tx, ty);
        return alignAround(rect, anchor, tx, ty);
    }
}
