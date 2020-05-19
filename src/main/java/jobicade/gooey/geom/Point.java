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
     * @return The sum of {@code this} and {@code point}.
     */
    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    /**
     * @return The sum of {@code this} and the point made of components
     * {@code x} and {@code y}.
     */
    public Point add(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    /**
     * @return The result of {@code this} subtracted by {@code point}.
     */
    public Point sub(Point point) {
        return new Point(x - point.x, y - point.y);
    }

    /**
     * @return The result of {@code this} subtracted by the point made of
     * components {@code x} and {@code y}.
     */
    public Point sub(int x, int y) {
        return new Point(this.x - x, this.y - y);
    }
}
