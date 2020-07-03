package jobicade.gooey.layout;

import java.util.List;

import jobicade.gooey.GooeyMath;
import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

public class GridLayout<T extends Layout> extends Layout {
    private List<T> cells;
    private MajorAxis majorAxis;

    /**
     * Creates an unbounded grid, a line along the minor axis.
     */
    public GridLayout(List<T> cells, MajorAxis majorAxis) {
        this.cells = cells;
        this.majorAxis = majorAxis;
    }

    private int lineSize = Integer.MAX_VALUE;
    /**
     * Sets the number of cells along the minor axis. After this amount, cells
     * begin to wrap onto the next line.
     */
    public void setLineSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Line must be at least 1 cell");
        }
        lineSize = size;
    }

    private float alignFactorX = 0.5f;
    private float alignFactorY = 0.5f;

    public void setCellAlignment(float facX, float facY) {
        alignFactorX = facX;
        alignFactorY = facY;
    }

    @Override
    public void apply(Rect requestedBounds) {
        int width = requestedBounds.getWidth();
        int height = requestedBounds.getHeight();

        Point shape = getShape();
        Point cellStep = new Point(width / shape.getX(), height / shape.getY());

        for (int i = 0; i < cells.size(); i++) {
            Point cellSize = cells.get(i).requestSize(cellStep);

            Rect outerCell = Rect.bySize(
                requestedBounds.interpolate(0, 0).add(getCellPos(i).scale(cellStep)),
                cellStep
            );

            cells.get(i).setBounds(Rect.bySize(Point.ZERO, cellSize)
                .alignInside(outerCell, alignFactorX, alignFactorY));
        }
        setBounds(requestedBounds);
    }

    @Override
    public Point requestSize(Point size) {
        return Point.biMax(size, getShape().scale(getLargestCell()));
    }

    private Point getCellPos(int i) {
        if (majorAxis == MajorAxis.ROW) {
            return new Point(i % lineSize, i / lineSize);
        } else {
            return new Point(i / lineSize, i % lineSize);
        }
    }

    private Point getShape() {
        int numLines = GooeyMath.ceilDiv(cells.size(), lineSize);

        if (majorAxis == MajorAxis.ROW) {
            return new Point(Math.min(lineSize, cells.size()), numLines);
        } else {
            return new Point(numLines, Math.min(lineSize, cells.size()));
        }
    }

    private Point getLargestCell() {
        Point size = cells.get(0).getMinSize();

        for (int i = 1; i < cells.size(); i++) {
            size = Point.biMax(size, cells.get(i).getMinSize());
        }
        return size;
    }

    public enum MajorAxis {
        ROW,
        COLUMN
    }
}
