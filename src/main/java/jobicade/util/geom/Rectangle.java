package jobicade.util.geom;

import java.io.Serializable;

public final class Rectangle implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Rectangle EMPTY = new Rectangle(0, 0, 0, 0);

    private final int x, y, width, height;

    private Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle rect) {
        this(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle(Point position, Point size) {
        this(position.getX(), position.getY(), size.getX(), size.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Rectangle) {
            Rectangle rectangle = (Rectangle)obj;
            return x == rectangle.x && y == rectangle.y &&
                width == rectangle.width && height == rectangle.height;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (((31 + x) * 31 + y) * 31 + width) * 31 + height;
    }

    @Override
    public String toString() {
        return String.format("{x: %d, y: %d, width: %d, height: %d}",
            x, y, width, height);
    }

    // Factories

    public static Rectangle empty() { return EMPTY; }

    public static Rectangle fromPositionSize(int x, int y, int width, int height) { return new Rectangle(x, y, width, height); }
    public static Rectangle fromPositionSize(Point position, Point size) { return new Rectangle(position.getX(), position.getY(), size.getX(), size.getY()); }
    public static Rectangle fromLeastMost(int left, int top, int right, int bottom) { return new Rectangle(left, top, right - left, bottom - top); }
    public static Rectangle fromLeastMost(Point least, Point most) { return new Rectangle(least.getX(), least.getY(), most.getX() - least.getX(), most.getY() - least.getY()); }

    // Getters and setters

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getLeft() { return x; }
    public int getTop() { return y; }
    public int getRight() { return x + width; }
    public int getBottom() { return y + height; }

    public Rectangle withX(int x) { return new Rectangle(x, y, width, height); }
    public Rectangle withY(int y) { return new Rectangle(x, y, width, height); }
    public Rectangle withWidth(int width) { return new Rectangle(x, y, width, height); }
    public Rectangle withHeight(int height) { return new Rectangle(x, y, width, height); }
    public Rectangle withLeft(int left) { return new Rectangle(left, y, x + width - left, height); }
    public Rectangle withTop(int top) { return new Rectangle(x, top, width, y + height - top); }
    public Rectangle withRight(int right) { return new Rectangle(x, y, right - x, height); }
    public Rectangle withBottom(int bottom) { return new Rectangle(x, y, width, bottom - y); }

    public Point getPosition() { return new Point(x, y); }
    public Point getSize() { return new Point(width, height); }
    public Point getLeast() { return new Point(x, y); }
    public Point getMost() { return new Point(x + width, y + height); }

    public Rectangle withLeast(int left, int top) { return new Rectangle(left, top, x + width - left, y + height - top); }
    public Rectangle withMost(int right, int bottom) { return new Rectangle(x, y, right - x, bottom - y); }

    // More common setters

    public Rectangle move(int x, int y) { return new Rectangle(x, y, width, height); }
    public Rectangle move(Point position) { return new Rectangle(position.getX(), position.getY(), width, height); }

    public Rectangle resize(int width, int height) { return new Rectangle(x, y, width, height); }
    public Rectangle resize(Point size) { return new Rectangle(x, y, size.getX(), size.getY()); }

    // Common operations

    public Rectangle translate(int x, int y) { return new Rectangle(this.x + x, this.y + y, width, height); }
    public Rectangle translate(Point offset) { return new Rectangle(this.x + offset.getX(), this.y + offset.getY(), width, height); }
    public Rectangle translate(Direction direction, int x) { return translate(direction.getUnit().scale(x, x)); }

    public Rectangle untranslate(int x, int y) { return new Rectangle(this.x - x, this.y - y, width, height); }
    public Rectangle untranslate(Point offset) { return new Rectangle(this.x - offset.getX(), this.y - offset.getY(), width, height); }

    // Boolean operations

    public boolean contains(int x, int y) { return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height; }
    public boolean contains(Point point) { return point.getX() >= x && point.getX() < x + width && point.getY() >= y && point.getY() < y + width; }

    public Rectangle union(Rectangle rect) {
        int x = Math.min(this.x, rect.x);
        int y = Math.min(this.y, rect.y);
        int width = Math.max(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.max(this.y + this.height, rect.x + rect.height) - y;

        return new Rectangle(x, y, width, height);
    }

    public Rectangle intersection(Rectangle rect) {
        int x = Math.max(this.x, rect.x);
        int y = Math.max(this.y, rect.y);
        int width = Math.min(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.min(this.y + this.height, rect.y + rect.height) - y;

        return new Rectangle(x, y, width, height);
    }
}
