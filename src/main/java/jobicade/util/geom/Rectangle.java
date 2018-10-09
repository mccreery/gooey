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
}
