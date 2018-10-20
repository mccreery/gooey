package jobicade.util.geom;

import net.minecraft.client.gui.ScaledResolution;

/**
 * A special type of point that represents a difference
 * or size between two points, for example the size of a rectangle.
 *
 * @see Rect
 */
public class Size extends Point {
    private static final long serialVersionUID = 1L;

    private static final Size ZERO = new Size();

    /**
     * @see Point#Point()
     */
    public Size() { super(); }

    /**
     * @see Point#Point(int, int)
     */
    public Size(int width, int height) {
        super(width, height);
    }

    /**
     * @see Point#Point(Point)
     */
    public Size(Point point) {
        super(point);
    }

    /**
     * @see Point#Point(ScaledResolution)
     */
    public Size(ScaledResolution resolution) {
        super(resolution);
    }

    /**
     * Returns a size instead of a basic point.
     * @see Point#zero()
     */
    public static Size zero() {
        return ZERO;
    }

    /**
     * Size implementation includes the values of width and height.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s{width: %d, height: %d}@%s",
            getClass().getName(), getX(), getY(), Integer.toHexString(hashCode()));
    }

    /**
     * @see #getX()
     */
    public int getWidth() {
        return getX();
    }

    /**
     * @see #getY()
     */
    public int getHeight() {
        return getY();
    }

    /**
     * @see #withX(int)
     */
    public Point withWidth(int width) {
        return withX(width);
    }

    /**
     * @see #withY(int)
     */
    public Point withHeight(int height) {
        return withY(height);
    }
}
