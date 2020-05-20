package jobicade.gooey.geom;

import java.io.Serializable;

import jobicade.gooey.Lerp;

/*
 * This class is very long. If your editor supports // region comments, you
 * should begin by folding them to make reading this bearable.
 */

/**
 * Represents an immutable axis-aligned rectangle in integer precision,
 * typically in screen pixel space.
 *
 * <ul>
 *   <li>Rects cannot have negative width or height
 *   <li>The minimum coordinate in either axis is inside the Rect
 *   <li>The maximum coordinate in either axis is outside the Rect
 * </ul>
 *
 * <p>Rects are created using {@link #bySize(Point, Point)},
 * {@link #byCorners(Point, Point)} or their overloads.
 */
public final class Rect implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Rect ZERO = new Rect(0, 0, 0, 0);

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private Rect(int x, int y, int width, int height) {
        if (width < 0) {
            throw new IllegalArgumentException("width < 0");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height < 0");
        }

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Rectangle objects are considered equal only to other Rectangle objects
     * with the same dimensions.
     * <p>{@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Rect) {
            Rect rectangle = (Rect)obj;
            return x == rectangle.x && y == rectangle.y &&
                width == rectangle.width && height == rectangle.height;
        }
        return super.equals(obj);
    }

    /**
     * Rectangle objects are considered equal only to other Rectangle objects
     * with the same dimensions.
     * <p>{@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (((31 + x) * 31 + y) * 31 + width) * 31 + height;
    }

    @Override
    public String toString() {
        return String.format("%s{x: %d, y: %d, width: %d, height: %d}",
            getClass().getName(), x, y, width, height);
    }

    // region Position-size representation

    /**
     * Creates a new Rect using position and size.
     */
    public static Rect bySize(Point position, Point size) {
        return new Rect(position.getX(), position.getY(), size.getX(), size.getY());
    }

    /**
     * Creates a new Rect using position and size.
     */
    public static Rect bySize(int x, int y, int width, int height) {
        return new Rect(x, y, width, height);
    }

    /**
     * Identical to {@link #getMinX()}.
     */
    public int getX() {
        return x;
    }

    /**
     * Identical to {@link #getMinY()}.
     */
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Identical to {@link #getMin()}.
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    public Point getSize() {
        return new Point(width, height);
    }

    /**
     * Copies this Rect with a new X position.
     *
     * @return A copy of {@code this}, but with its X position replaced by
     * {@code x}. The width remains the same.
     * @see #withMinX()
     */
    public Rect withX(int x) {
        return new Rect(x, y, width, height);
    }

    /**
     * Copies this Rect with a new Y position.
     *
     * @return A copy of {@code this}, but with its Y position replaced by
     * {@code y}. The height remains the same.
     * @see #withMinY()
     */
    public Rect withY(int y) {
        return new Rect(x, y, width, height);
    }

    /**
     * Copies this Rect with a new width.
     *
     * @return A copy of {@code this}, but with its width replaced by
     * {@code width}.
     */
    public Rect withWidth(int width) {
        return new Rect(x, y, width, height);
    }

    /**
     * Copies this Rect with a new height.
     *
     * @return A copy of {@code this}, but with its height replaced by
     * {@code height}.
     */
    public Rect withHeight(int height) {
        return new Rect(x, y, width, height);
    }

    /**
     * Copies this Rect with a new position.
     *
     * @return A copy of {@code this}, but with its position replaced by
     * {@code position}. The size remains the same.
     */
    public Rect withPosition(Point position) {
        return new Rect(position.getX(), position.getY(), width, height);
    }

    /**
     * Copies this Rect with a new size.
     *
     * @return A copy of {@code this}, but with its size replaced by
     * {@code size}.
     */
    public Rect withSize(Point size) {
        return new Rect(x, y, size.getX(), size.getY());
    }

    // endregion

    // region Min-max representation

    /**
     * Creates a new Rect using its minimum and maximum corners.
     */
    public static Rect byCorners(Point min, Point max) {
        return bySize(min, max.sub(min));
    }

    /**
     * Creates a new Rect using its minimum and maximum corners.
     */
    public static Rect byCorners(int minX, int minY, int maxX, int maxY) {
        return new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Identical to {@link #getX()}.
     */
    public int getMinX() {
        return x;
    }

    /**
     * Identical to {@link #getY()}.
     */
    public int getMinY() {
        return y;
    }

    public int getMaxX() {
        return x + width;
    }

    public int getMaxY() {
        return y + height;
    }

    /**
     * Identical to {@link #getPosition()}.
     */
    public Point getMin() {
        return new Point(x, y);
    }

    public Point getMax() {
        return new Point(x + width, y + height);
    }

    /**
     * Copies this Rect with a new minimum X.
     *
     * @return A copy of {@code this}, but with its minimum X replaced by
     * {@code minX}. The maximum X remains the same.
     * @see #withX()
     */
    public Rect withMinX(int minX) {
        return new Rect(minX, y, x + width - minX, height);
    }

    /**
     * Copies this Rect with a new minimum Y.
     *
     * @return A copy of {@code this}, but with its minimum Y replaced by
     * {@code minY}. The maximum Y remains the same.
     * @see #withY()
     */
    public Rect withMinY(int minY) {
        return new Rect(x, minY, width, y + height - minY);
    }

    /**
     * Copies this Rect with a new maximum X.
     *
     * @return A copy of {@code this}, but with its maximum X replaced by
     * {@code maxX}. The minimum X remains the same.
     */
    public Rect withMaxX(int maxX) {
        return new Rect(x, y, maxX - x, height);
    }

    /**
     * Copies this Rect with a new maximum Y.
     *
     * @return A copy of {@code this}, but with its maximum Y replaced by
     * {@code maxY}. The minimum Y remains the same.
     */
    public Rect withMaxY(int maxY) {
        return new Rect(x, y, width, maxY - y);
    }

    /**
     * Copies this Rect with a new minimum corner.
     *
     * @return A copy of {@code this}, but with its minimum corner replaced by
     * {@code min}. The maximum corner remains the same.
     */
    public Rect withMin(Point min) {
        return new Rect(min.getX(), min.getY(), x + width - min.getX(), y + height - min.getY());
    }

    /**
     * Copies this Rect with a new maximum corner.
     *
     * @return A copy of {@code this}, but with its maximum corner replaced by
     * {@code max}. The minimum corner remains the same.
     */
    public Rect withMax(Point max) {
        return new Rect(x, y, max.getX() - x, max.getY() - y);
    }

    // endregion

    // region Operations

    public Rect translate(Point offset) {
        return new Rect(x + offset.getX(), y + offset.getY(), width, height);
    }

    public Rect translate(int x, int y) {
        return new Rect(this.x + x, this.y + y, width, height);
    }

    /**
     * Copies this Rect with padding.
     *
     * @return A copy of {@code this},
     * but padded by {@code padding} on each side.
     */
    public Rect grow(int padding) {
        return new Rect(x - padding, y - padding, width + 2 * padding, height + 2 * padding);
    }

    /**
     * Interpolates a coordinate inside the Rect.
     *
     * @param tx X parameter between 0 (for min X) and 1 (for max X).
     * @param ty Y parameter between 0 (for min Y) and 1 (for max Y).
     * @return The result of interpolating in X and Y.
     */
    public Point interpolate(float tx, float ty) {
        return new Point(
            Lerp.lerp(x, x + width, tx),
            Lerp.lerp(y, y + height, ty)
        );
    }

    /**
     * @return The smallest Rect containing all points
     * inside both {@code this} and {@code rect}.
     */
    public Rect union(Rect rect) {
        int x = Math.min(this.x, rect.x);
        int y = Math.min(this.y, rect.y);
        int width = Math.max(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.max(this.y + this.height, rect.y + rect.height) - y;

        return new Rect(x, y, width, height);
    }

    /**
     * @return The largest Rect containing only points
     * inside both {@code this} and {@code rect}.
     */
    public Rect intersect(Rect rect) {
        int x = Math.max(this.x, rect.x);
        int y = Math.max(this.y, rect.y);
        int width = Math.min(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.min(this.y + this.height, rect.y + rect.height) - y;

        return new Rect(x, y, width, height);
    }

    // endregion

    // region Conditionals

    /**
     * @return {@code true} if either width or height is zero.
     */
    public boolean isEmpty() {
        return width == 0 || height == 0;
    }

    public boolean contains(Point point) {
        return point.getX() >= x && point.getY() >= y
            && point.getX() < x + width && point.getY() < y + height;
    }

    public boolean contains(int x, int y) {
        return x >= x && x < x + width
            && y >= y && y < y + height;
    }

    // endregion
}
