package jobicade.util.geom;

import java.io.Serializable;

/**
 * Represents an immutable axis-aligned rectangle in integer precision,
 * typically in screen pixel space. Rectangles with nonpositive width or height
 * are considered empty, and rectangles with negative width or height are
 * considered denormal and may behave in strange ways. For example, union and
 * intersection behave like each other when operating on denormal rectangles.
 *
 * <p>Use {@link #normalize()} to ensure rectangles are normal.
 */
public final class Rectangle implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Rectangle EMPTY = new Rectangle(0, 0, 0, 0);

    private final int x, y, width, height;

    /**
     * Default constructor for rectangles. All values will be zero.
     */
    public Rectangle() { this(0, 0, 0, 0); }

    private Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Copy constructor for rectangles.
     * @param rect The original rectangle to copy.
     */
    public Rectangle(Rectangle rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }

    /**
     * Rectangle objects are considered equal only to other rectangle objects
     * with the same dimensions.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Rectangle) {
            Rectangle rectangle = (Rectangle)obj;
            return x == rectangle.x && y == rectangle.y &&
                width == rectangle.width && height == rectangle.height;
        }
        return super.equals(obj);
    }

    /**
     * Rectangle objects are considered equal only to other rectangle objects
     * with the same dimensions.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (((31 + x) * 31 + y) * 31 + width) * 31 + height;
    }

    /**
     * Rectangle implementation includes the dimensions.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s{x: %d, y: %d, width: %d, height: %d}@%s",
            getClass().getName(), x, y, width, height, Integer.toHexString(hashCode()));
    }

    // Factories

    /**
     * Returns an empty rectangle. Prefer to use this over creating one,
     * as there may be a performance benefit.
     * @return An empty rectangle.
     */
    public static Rectangle empty() { return EMPTY; }

    /**
     * Returns a rectangle with the given top left position and size.
     *
     * @param x The leftmost X coordinate of the rectangle. Same as "left".
     * @param y The topmost Y coordinate of the rectangle. Same as "top".
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @return A rectangle with the given top left position and size.
     */
    public static Rectangle fromPositionSize(int x, int y, int width, int height) { return new Rectangle(x, y, width, height); }

    /**
     * Returns a rectangle with the given top left position and size.
     *
     * @param position The top left position of the rectangle.
     * @param size The size of the rectangle.
     * @return A rectangle with the given top left position and size.
     */
    public static Rectangle fromPositionSize(Point position, Point size) { return new Rectangle(position.getX(), position.getY(), size.getX(), size.getY()); }

    /**
     * Returns a rectangle with the given top left and bottom right coordinates.
     *
     * @param left The leftmost X coordinate of the rectangle. Same as "x".
     * @param top The topmost Y coordinate of the rectangle. Same as "y".
     * @param right The rightmost X coordinate of the rectangle.
     * @param bottom The bottommost Y coordinate of the rectangle.
     * @return A rectangle with the given top left and bottom right coordinates.
     */
    public static Rectangle fromLeastMost(int left, int top, int right, int bottom) { return new Rectangle(left, top, right - left, bottom - top); }

    /**
     * Returns a rectangle with the given top left and bottom right coordinates.
     *
     * @param least The top left position of the rectangle.
     * @param most The bottom right position of the rectangle.
     * @return A rectangle with the given top left and bottom right coordinates.
     */
    public static Rectangle fromLeastMost(Point least, Point most) { return new Rectangle(least.getX(), least.getY(), most.getX() - least.getX(), most.getY() - least.getY()); }

    // Getters and setters

    /**
     * @return The leftmost X coordinate of the rectangle. Same as "left".
     * @see #getLeft()
     */
    public int getX() { return x; }

    /**
     * @return The topmost Y coordinate of the rectangle. Same as "top".
     * @see #getTop()
     */
    public int getY() { return y; }

    /**
     * @return The width of the rectangle.
     */
    public int getWidth() { return width; }

    /**
     * @return The height of the rectangle.
     */
    public int getHeight() { return height; }

    /**
     * @return The leftmost X coordinate of the rectangle. Same as "x".
     * @see #getX()
     */
    public int getLeft() { return x; }

    /**
     * @return The topmost Y coordinate of the rectangle. Same as "y".
     * @see #getY()
     */
    public int getTop() { return y; }

    /**
     * @return The rightmost X coordinate of the rectangle.
     */
    public int getRight() { return x + width; }

    /**
     * @return The bottommost Y coordinate of the rectangle.
     */
    public int getBottom() { return y + height; }

    /**
     * Returns a near identical rectangle with the given leftmost X coordinate.
     * This method differs from {@link #withLeft(int)} in that the width of the
     * rectangle remains the same. There is no guarantee that the returned
     * rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #move(int, int)} to change both X and Y.
     *
     * @param x The new leftmost X coordinate.
     * @return A near identical rectangle with the given leftmost X coordinate.
     */
    public Rectangle withX(int x) { return new Rectangle(x, y, width, height); }

    /**
     * Returns a near identical rectangle with the given leftmost Y coordinate.
     * This method differs from {@link #withTop(int)} in that the height of the
     * rectangle remains the same. There is no guarantee that the returned
     * rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #move(int, int)} to change both X and Y.
     *
     * @param y The new leftmost Y coordinate.
     * @return A near identical rectangle with the given leftmost Y coordinate.
     */
    public Rectangle withY(int y) { return new Rectangle(x, y, width, height); }

    /**
     * Returns a near identical rectangle with the given width. The top left
     * position of the rectangle remains the same. There is no guarantee that
     * the returned rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #resize(int, int)} to change both width and height.
     *
     * @param width The new width.
     * @return A near identical rectangle with the given width.
     */
    public Rectangle withWidth(int width) { return new Rectangle(x, y, width, height); }

    /**
     * Returns a near identical rectangle with the given height. The top left
     * position of the rectangle remains the same. There is no guarantee that
     * the returned rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #resize(int, int)} to change both width and height.
     *
     * @param height The new height.
     * @return A near identical rectangle with the given height.
     */
    public Rectangle withHeight(int height) { return new Rectangle(x, y, width, height); }

    /**
     * Returns a near identical rectangle with the given leftmost X coordinate.
     * This method differs from {@link #withX(int)} in that the rightmost
     * X coordinate remains the same. There is no guarantee that the returned
     * rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withLeast(int, int)} to change both X and Y.
     *
     * @param left The new leftmost X coordinate
     * @return A near copy of this rectangle with the given leftmost X coordinate
     */
    public Rectangle withLeft(int left) { return new Rectangle(left, y, x + width - left, height); }

    /**
     * Returns a near identical rectangle with the given topmost Y coordinate.
     * This method differs from {@link #withY(int)} in that the bottommost
     * Y coordinate remains the same. There is no guarantee that the returned
     * rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withLeast(int, int)} to change both X and Y.
     *
     * @param top The new topmost Y coordinate
     * @return A near copy of this rectangle with the given topmost Y coordinate
     */
    public Rectangle withTop(int top) { return new Rectangle(x, top, width, y + height - top); }

    /**
     * Returns a near identical rectangle with the given rightmost X coordinate.
     * The top left position of the rectangle remains the same. There is no
     * guarantee that the returned rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withMost(int, int)} to change both width and height.
     *
     * @param right The new rightmost X coordinate.
     * @return A near identical rectangle with the given rightmost X coordinate.
     */
    public Rectangle withRight(int right) { return new Rectangle(x, y, right - x, height); }

    /**
     * Returns a near identical rectangle with the given bottommost Y coordinate.
     * The top left position of the rectangle remains the same. There is no
     * guarantee that the returned rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withMost(int, int)} to change both width and height.
     *
     * @param bottom The new bottommost Y coordinate.
     * @return A near identical rectangle with the given bottommost Y coordinate.
     */
    public Rectangle withBottom(int bottom) { return new Rectangle(x, y, width, bottom - y); }

    /**
     * @return The top left position of the rectangle. Same as "least".
     * @see #getLeast()
     */
    public Point getPosition() { return new Point(x, y); }

    /**
     * @return The size of the rectangle.
     */
    public Point getSize() { return new Point(width, height); }

    /**
     * @return The top left coordinate of the rectangle. Same as "position".
     * @see #getPosition()
     */
    public Point getLeast() { return new Point(x, y); }

    /**
     * @return The bottom right coordinate of the rectangle.
     */
    public Point getMost() { return new Point(x + width, y + height); }

    /**
     * Returns a near identical rectangle with the given top left coordinate.
     * This method differs from {@link #move(int, int)} in that the bottom right
     * coordinate remains the same. There is no guarantee that the returned
     * rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withLeft(int)} or {@link #withTop(int)} to change
     * either left or top, but not both.
     *
     * @param left The new leftmost X coordinate.
     * @param top The new topmost Y coordinate.
     * @return A near identical rectangle with the given top left coordinate.
     */
    public Rectangle withLeast(int left, int top) { return new Rectangle(left, top, x + width - left, y + height - top); }

    /**
     * @param least The new top left coordinate.
     * @see #withLeast(int, int)
     */
    public Rectangle withLeast(Point least) { return new Rectangle(least.getX(), least.getY(), x + width - least.getX(), y + height - least.getY()); }

    /**
     * Returns a near identical rectangle with the given bottom right coordinate.
     * The top left coordinate remains the same. There is no guarantee that the
     * returned rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withRight(int)} or {@link #withBottom(int)} to change
     * either right or bottom, but not both.
     *
     * @param right The new rightmost X coordinate.
     * @param bottom The new bottommost Y coordinate.
     * @return A near identical rectangle with the given bottom right coordinate.
     */
    public Rectangle withMost(int right, int bottom) { return new Rectangle(x, y, right - x, bottom - y); }

    /**
     * @param most The new bottom right coordinate.
     * @see #withMost(int, int)
     */
    public Rectangle withMost(Point most) { return new Rectangle(x, y, most.getX() - x, most.getY() - y); }

    // More common setters

    /**
     * Returns a near identical rectangle with the given top left position.
     * This method differs from {@link #withLeast(int, int)} in that the size
     * remains the same. There is no guarantee that the returned rectangle will
     * be distinct from {@code this}.
     *
     * <p>Prefer {@link #withX(int)} or {@link #withY(int)} to change
     * either X or Y, but not both.
     *
     * @param x The new leftmost X position.
     * @param y The new topmost Y position.
     * @return A near identical rectangle with the given top left position.
     */
    public Rectangle move(int x, int y) { return new Rectangle(x, y, width, height); }

    /**
     * @param position The new top left position.
     * @see #move(int, int)
     */
    public Rectangle move(Point position) { return new Rectangle(position.getX(), position.getY(), width, height); }

    /***
     * Returns a near identical rectangle with the given size. The top left
     * position remains the same. There is no guarantee that the returned
     * rectangle will be distinct from {@code this}.
     *
     * <p>Prefer {@link #withWidth(int)} or {@link #withHeight(int)} to change
     * either width or height, but not both.
     *
     * @param width The new width of the rectangle.
     * @param height The new height of the rectangle.
     * @return A near identical rectangle with the given size.
     */
    public Rectangle resize(int width, int height) { return new Rectangle(x, y, width, height); }

    /**
     * @param size The new size of the rectangle.
     * @see #resize(int, int)
     */
    public Rectangle resize(Point size) { return new Rectangle(x, y, size.getX(), size.getY()); }

    // Common operations

    /**
     * Returns the result of translating this rectangle. There is no guarantee
     * that the returned rectangle will be distinct from {@code this}.
     *
     * @param x The X coordinate offset.
     * @param y The Y coordinate offset.
     * @return The result of translating this rectangle by X and Y.
     */
    public Rectangle translate(int x, int y) { return new Rectangle(this.x + x, this.y + y, width, height); }

    /**
     * @param offset The offset.
     * @see #translate(int, int)
     */
    public Rectangle translate(Point offset) { return new Rectangle(this.x + offset.getX(), this.y + offset.getY(), width, height); }

    /**
     * Returns the result of translating this rectangle in the given direction
     * by the given distance. There is no guarantee that the returned rectangle
     * will be distinct from {@code this}.
     *
     * @param direction The direction of translation.
     * @param x The distance of translation.
     * @return The result of translating this rectangle in the given direction
     * by the given amount.
     */
    public Rectangle translate(Direction direction, int x) { return translate(direction.getUnit().scale(x, x)); }

    /**
     * As {@link #translate(int, int)}, but inverts the translation.
     *
     * @param x The X coordinate offset.
     * @param y The Y coordinate offset.
     * @return The result of translating this rectangle by X and Y.
     */
    public Rectangle untranslate(int x, int y) { return new Rectangle(this.x - x, this.y - y, width, height); }

    /**
     * @param offset The offset.
     * @see #untranslate(int, int)
     */
    public Rectangle untranslate(Point offset) { return new Rectangle(this.x - offset.getX(), this.y - offset.getY(), width, height); }

    // Boolean operations

    /**
     * Tests whether the given point is inside this rectangle. Points are
     * considered inside using the top left coordinate as an inclusive lower
     * bound and the bottom right coordinate as an exclusive upper bound.
     *
     * @param x The X coordinate of the point.
     * @param y The Y coordinate of the point.
     * @return {@code true} if the point is inside this rectangle.
     */
    public boolean contains(int x, int y) { return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height; }

    /**
     * @param point The point.
     * @see #contains(int, int)
     */
    public boolean contains(Point point) { return point.getX() >= x && point.getX() < x + width && point.getY() >= y && point.getY() < y + width; }

    /**
     * Tests whether the rectangle is empty. An empty rectangle is any
     * rectangle which contains no points. Equivalently, empty rectangles are
     * either denormal or have a width or height of zero.
     *
     * @return {@code true} if the rectangle is empty.
     * @see #isNormal()
     */
    public boolean isEmpty() { return width <= 0 || height <= 0; }

    /**
     * Tests whether the rectangle is normal. A normal rectangle has a strictly
     * positive width and height. Denormal rectangles behave differently for
     * certain operations, see {@link Rectangle} for details.
     *
     * @return {@code true} if the rectangle is normal.
     */
    public boolean isNormal() { return width > 0 && height > 0; }

    /**
     * Returns an equivalent rectangle that is normal. The result must behave
     * normally for all operations. See {@link Rectangle} for details.
     *
     * @return An equivalent rectangle that is normal.
     */
    public Rectangle normalize() {
        if(isNormal()) return this;
        int x = this.x, y = this.y, width = this.width, height = this.height;

        if(width < 0) {
            x += width;
            width = -width;
        }
        if(height < 0) {
            y += height;
            height = -height;
        }
        return new Rectangle(x, y, width, height);
    }

    /**
     * Returns the result of the union operation over two rectangles. The result
     * must contain at least all points in either rectangle.
     *
     * <p>This function behaves like {@link #intersection(Rectangle)} on
     * denormal rectangles.
     *
     * @param rect The other rectangle to union with.
     * @return The result of the union operation over two rectangles.
     */
    public Rectangle union(Rectangle rect) {
        int x = Math.min(this.x, rect.x);
        int y = Math.min(this.y, rect.y);
        int width = Math.max(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.max(this.y + this.height, rect.x + rect.height) - y;

        return new Rectangle(x, y, width, height);
    }

    /**
     * Returns the result of the intersection operation over two rectangles.
     * The result must contain exactly all points in both rectangles.
     *
     * <p>This function behaves like {@link #intersection(Rectangle)} on
     * denormal rectangles.
     *
     * @param rect The other rectangle to union with.
     * @return The result of the union operation over two rectangles.
     */
    public Rectangle intersection(Rectangle rect) {
        int x = Math.max(this.x, rect.x);
        int y = Math.max(this.y, rect.y);
        int width = Math.min(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.min(this.y + this.height, rect.y + rect.height) - y;

        return new Rectangle(x, y, width, height);
    }
}
