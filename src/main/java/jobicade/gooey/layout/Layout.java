package jobicade.gooey.layout;

import com.google.common.collect.Range;

import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

/**
 * Tracks the position and size of a movable rectangular object. Code using a
 * layout is expected to first request a size ({@link #requestSize(Point)}) then
 * use that size to set bounds for the layout ({@link #setBounds(Rect)}).
 *
 * <p>Layout implementations should avoid size constraints that are likely to
 * change between layout and rendering.
 */
public abstract class Layout {
    /**
     * @return A new static layout accepting a fixed size.
     */
    public static Layout fixedSize(Point size) {
        return new StaticLayout(size, Range.singleton(size.getX()), Range.singleton(size.getY()));
    }

    /**
     * @return A new static layout accepting any size with a size hint.
     */
    public static Layout anySize(Point sizeHint) {
        return new StaticLayout(sizeHint, Range.atLeast(0), Range.atLeast(0));
    }

    /**
     * @return A new static layout accepting sizes within range and with a size hint.
     */
    public static Layout sizeRange(Point sizeHint, Range<Integer> widthRange, Range<Integer> heightRange) {
        return new StaticLayout(sizeHint, widthRange, heightRange);
    }

    private Rect bounds;
    public Rect getBounds() {
        return bounds;
    }

    /**
     * The size of the bounds is checked during this method.
     *
     * @throws IllegalArgumentException If {@code bounds} has an invalid size.
     * @see #checkSize(Point)
     */
    public void setBounds(Rect bounds) {
        if (!checkSize(bounds.getSize())) {
            throw new IllegalArgumentException("Invalid size");
        }
        this.bounds = bounds;
    }

    /**
     * @return {@code true} if {@code size} is valid for this layout.
     */
    public boolean checkSize(Point size) {
        return size.equals(requestSize(size));
    }

    /**
     * Chooses a valid size in response to a requested size. If the argument
     * is a valid size, the return value should be equal to it. For example, a
     * layout with a maximum size of (100,100) should return (50,50) in response
     * to (50,50) but (100,100) in response to (150,150).
     *
     * @return A valid size.
     * @see #checkSize(Point)
     */
    public abstract Point requestSize(Point size);

    /**
     * @return A valid size that should be used for this layout given no further
     * constraints.
     */
    public abstract Point getSizeHint();
}
