package jobicade.gooey.layout;

import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

public class Align {
    /**
     * Moves a Rect such that an interpolated point inside it is aligned with
     * the anchor point.
     *
     * @param tx The X interpolation parameter.
     * @param ty The Y interpolation parameter.
     * @return The moved copy of {@code rect}.
     * @see Rect#interpolate(float, float)
     */
    public static Rect alignAround(Rect rect, Point anchor, float tx, float ty) {
        Point toAlign = rect.getSize().scale(tx, ty);
        return rect.withPosition(anchor.sub(toAlign));
    }

    /**
     * Moves a Rect such that the corresponding interpolated points inside
     * {@code rect} and {@code container} are aligned.
     *
     * @param tx The X interpolation parameter.
     * @param ty The Y interpolation parameter.
     * @return The moved copy of {@code rect}.
     * @see Rect#interpolate(float, float)
     */
    public static Rect alignInside(Rect rect, Rect container, float tx, float ty) {
        Point anchor = container.interpolate(tx, ty);
        return alignAround(rect, anchor, tx, ty);
    }
}
