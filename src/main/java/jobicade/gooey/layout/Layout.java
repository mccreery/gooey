package jobicade.gooey.layout;

import com.google.common.collect.Range;

import jobicade.gooey.GooeyMath;
import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

/**
 * Tracks the position and size of a movable rectangular object.
 */
public final class Layout {
    /**
     * @return A new layout accepting a fixed size.
     */
    public static Layout fixedSize(Point size) {
        return new Layout(size, Range.singleton(size.getX()), Range.singleton(size.getY()));
    }

    /**
     * @return A new layout accepting any size with a size hint.
     */
    public static Layout anySize(Point sizeHint) {
        return new Layout(sizeHint, Range.atLeast(0), Range.atLeast(0));
    }

    /**
     * @return A new layout accepting sizes within range and with a size hint.
     */
    public static Layout sizeRange(Point sizeHint, Range<Integer> widthRange, Range<Integer> heightRange) {
        return new Layout(sizeHint, widthRange, heightRange);
    }

    private final Range<Integer> widthRange;
    private final Range<Integer> heightRange;

    private Layout(Point sizeHint, Range<Integer> widthRange, Range<Integer> heightRange) {
        if (sizeHint.getX() < 0 || sizeHint.getY() < 0) {
            throw new IllegalArgumentException("Negative size hint");
        }

        this.sizeHint = sizeHint;
        this.widthRange = widthRange;
        this.heightRange = heightRange;

        if (widthRange.isEmpty() || heightRange.isEmpty()) {
            throw new IllegalArgumentException("Empty size range");
        } else if (GooeyMath.lowerBound(widthRange) < 0 || GooeyMath.lowerBound(heightRange) < 0) {
            throw new IllegalArgumentException("Negative size range");
        } else if (!checkSize(sizeHint)) {
            throw new IllegalArgumentException("Size hint out of range");
        }
    }

    /**
     * @return {@code true} if {@code size} is valid.
     */
    public boolean checkSize(Point size) {
        return widthRange.contains(size.getX()) && heightRange.contains(size.getY());
    }

    /**
     * @return The closest valid size to {@code size}.
     */
    public Point clampSize(Point size) {
        return new Point(GooeyMath.clamp(widthRange, size.getX()), GooeyMath.clamp(heightRange, size.getY()));
    }

    private final Point sizeHint;
    /**
     * The size hint should be chosen as the size of the layout if there are no
     * further external decisions or constraints.
     */
    public Point getSizeHint() {
        return sizeHint;
    }

    private Rect bounds;
    public Rect getBounds() {
        return bounds;
    }

    /**
     * @throws IllegalArgumentException If {@code bounds} has an invalid size.
     * @see #checkSize(Point)
     */
    public void setBounds(Rect bounds) {
        if (!checkSize(bounds.getSize())) {
            throw new IllegalArgumentException("Invalid size");
        }
        this.bounds = bounds;
    }
}
