package jobicade.util.geom;

import java.io.Serializable;

import net.minecraft.client.gui.ScaledResolution;

public final class Point implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Point ZERO = new Point(0, 0);

    public final int x, y;

    public Point() { this(0, 0); }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point(ScaledResolution resolution) {
        this.x = resolution.getScaledWidth();
        this.y = resolution.getScaledHeight();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point) {
            Point point = (Point)obj;
            return x == point.x && y == point.y;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (31 + x) * 31 + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Point add(int x, int y) { return new Point(this.x + x, this.y + y); }
    public Point add(Point point) { return new Point(x + point.x, y + point.y); }

    public Point sub(int x, int y) { return new Point(this.x - x, this.y - y); }
    public Point sub(Point point) { return new Point(x - point.x, y - point.y); }

    public Point invert() { return new Point(-x, -y); }

    public Point scale(float xf, float yf) {
        return new Point(Math.round(x * xf), Math.round(y * yf));
    }

    public Point scale(float xf, float yf, int x, int y) {
        return new Point(
            Math.round((this.x - x) * xf + x),
            Math.round((this.y - y) * yf + y));
    }

    public Point scale(float xf, float yf, Point point) {
        return scale(xf, yf, point.x, point.y);
    }
}
