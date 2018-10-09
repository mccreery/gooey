package jobicade.util.geom;

import java.io.Serializable;

public final class Rectangle implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Rectangle EMPTY = new Rectangle();

    public final int x, y, width, height;

    public Rectangle() { this(0, 0, 0, 0); }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle rect) {
        this(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle(Point position, Point size) {
        this(position.x, position.y, size.x, size.y);
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

    public Rectangle move(int x, int y) { return new Rectangle(x, y, width, height); }
    public Rectangle move(Point position) { return new Rectangle(position.x, position.y, width, height); }

    public Rectangle resize(int width, int height) { return new Rectangle(x, y, width, height); }
    public Rectangle resize(Point size) { return new Rectangle(x, y, size.x, size.y); }

    public Rectangle translate(int x, int y) { return new Rectangle(this.x + x, this.y + y, width, height); }
    public Rectangle translate(Point offset) { return new Rectangle(this.x + offset.x, this.y + offset.y, width, height); }

    public Rectangle untranslate(int x, int y) { return new Rectangle(this.x - x, this.y - y, width, height); }
    public Rectangle untranslate(Point offset) { return new Rectangle(this.x - offset.x, this.y - offset.y, width, height); }
}
