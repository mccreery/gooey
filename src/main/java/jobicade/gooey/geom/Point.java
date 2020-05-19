package jobicade.gooey.geom;

import java.io.Serializable;

/**
 * Represents an immutable 2D point in integer precision, typically in screen
 * pixel space.
 */
public class Point implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Point ZERO = new Point(0, 0);

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    /**
     * Points are equal only to other Points with equal X and Y.
     * <p>{@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point) {
            Point point = (Point)obj;
            return x == point.x && y == point.y;
        }
        return super.equals(obj);
    }

    /**
     * Points are equal only to other Points with equal X and Y.
     * <p>{@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (31 + x) * 31 + y;
    }

    @Override
    public String toString() {
        return String.format("%s{x: %d, y: %d}",
            getClass().getName(), x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Copies this Point with a new X value.
     *
     * @return A copy of {@code this},
     * but with its X coordinate replaced with {@code x}.
     */
    public Point withX(int x) {
        return new Point(x, y);
    }

    /**
     * Copies this Point with a new Y value.
     *
     * @return A copy of {@code this},
     * but with its Y coordinate replaced with {@code y}.
     */
    public Point withY(int y) {
        return new Point(x, y);
    }

    /**
     * Returns the sum of this point and another point.
     * @param x The X coordinate of the other point.
     * @param y The Y coordinate of the other point.
     * @return The sum of this point and the other point.
     */
    public Point add(int x, int y) { return new Point(this.x + x, this.y + y); }

    /**
     * @param point The other point.
     * @return The result of moving this point in the given direction
     * @see #add(int, int)
     */
    public Point add(Point point) { return new Point(x + point.x, y + point.y); }

    /**
     * Returns the result of moving a point in a direction by a distance.
     * @param direction The direction of movement.
     * @param x The distance to move.
     * @return The result of moving this point in the given direction
     * by the given distance.
     */
    public Point add(Direction direction, int x) { return add(direction.getUnit().scale(x, x)); }

    /**
     * Returns the difference between this point and another point.
     * @param x The X coordinate of the other point.
     * @param y The Y coordinate of the other point.
     * @return The difference between this point and the other point.
     */
    public Size sub(int x, int y) { return new Size(this.x - x, this.y - y); }

    /**
     * @param point The other point
     * @return The difference between this point and the other point.
     * @see #sub(int, int)
     */
    public Size sub(Point point) { return new Size(x - point.x, y - point.y); }

    /**
     * Returns a point with both X and Y negated.
     * @return A point with both X and Y negated.
     */
    public Point invert() { return new Point(-x, -y); }

    /**
     * Scales the point by a factor in X and Y.
     * @param xf The factor in the X axis.
     * @param yf The factor in the Y axis.
     * @return A point scaled by the given factors.
     */
    public Point scale(float xf, float yf) {
        return new Point(Math.round(x * xf), Math.round(y * yf));
    }

    /**
     * Scales the point by a factor in X and Y.
     *
     * @param factor The scaling factor.
     * @return A point scaled by the given factor.
     */
    public Point scale(Point factor) {
        return new Point(x * factor.x, y * factor.y);
    }

    /**
     * Scales the point by a factor in X and Y around a point.
     *
     * @param xf The factor in the X axis.
     * @param yf The factor in the Y axis.
     * @param x The point to scale around X coordinate.
     * @param y The point to scale around Y coordinate.
     * @return A point scaled by the given factors around the given point.
     * @see #scale(float, float)
     */
    public Point scale(float xf, float yf, int x, int y) {
        return new Point(
            Math.round((this.x - x) * xf + x),
            Math.round((this.y - y) * yf + y));
    }

    /**
     * @param xf The factor in the X axis.
     * @param yf The factor in the Y axis.
     * @param point The point.
     * @return A point scaled by the given factors around the given point.
     * @see #scale(float, float, int, int)
     */
    public Point scale(float xf, float yf, Point point) {
        return scale(xf, yf, point.x, point.y);
    }
}
