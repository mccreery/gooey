package jobicade.gooey.layout;

import com.google.common.collect.Range;

import jobicade.gooey.GooeyMath;
import jobicade.gooey.geom.Point;

// package-private
final class StaticLayout extends Layout {
    private final Point sizeHint;
    private final Range<Integer> widthRange;
    private final Range<Integer> heightRange;

    // package-private
    StaticLayout(Point sizeHint, Range<Integer> widthRange, Range<Integer> heightRange) {
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

    @Override
    public boolean checkSize(Point size) {
        return widthRange.contains(size.getX()) && heightRange.contains(size.getY());
    }

    @Override
    public Point requestSize(Point size) {
        return new Point(GooeyMath.clamp(widthRange, size.getX()), GooeyMath.clamp(heightRange, size.getY()));
    }

    @Override
    public Point getSizeHint() {
        return sizeHint;
    }
}
