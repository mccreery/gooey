package jobicade.util.render.element;

import java.util.List;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.builder.Builder;

import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;

public class GridBuilder<T extends GuiElement> implements Builder<GuiElement> {
    private List<T> source;
    private int columns = -1;

	private Direction flowDirection;
	private boolean transpose;
    private Direction cellAlignment;

    private GridSpacingPolicy spacingPolicy = cellSize -> cellSize;

    public GridBuilder(List<T> source) {
        this.source = source;
    }

    public List<T> getSource() {
        return source;
    }

    public GridBuilder<T> setSource(List<T> source) {
        this.source = source;
        return this;
    }

    public int getColumns() {
        return columns;
    }

    public GridBuilder<T> setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public Direction getFlowDirection() {
        return flowDirection;
    }

    public GridBuilder<T> setFlowDirection(Direction flowDirection) {
        if(flowDirection.getRow() == 1 || flowDirection.getCol() == 1) {
            throw new IllegalArgumentException("Flow direction must be a diagonal");
        }
        this.flowDirection = flowDirection;
        return this;
    }

    public boolean getTransposed() {
        return transpose;
    }

    public GridBuilder<T> setTransposed(boolean transposed) {
        this.transpose = transposed;
        return this;
    }

    public Direction getCellAlignment() {
        return cellAlignment;
    }

    public GridBuilder<T> setCellAlignment(Direction cellAlignment) {
        this.cellAlignment = cellAlignment;
        return this;
    }

    public GridSpacingPolicy getSpacingPolicy() {
        return spacingPolicy;
    }

    public GridBuilder<T> setSpacingPolicy(GridSpacingPolicy policy) {
        this.spacingPolicy = policy;
        return this;
    }

    @Override
    public GuiElement build() {
        List<T> source = ImmutableList.copyOf(this.source);

        Point cellSize = getMaxCellSize();
        Point cellPitch = spacingPolicy.getPitch(cellSize);

        boolean transpose = this.transpose;
        int columns = this.columns;

        if(columns <= 0) {
            transpose = !transpose;
            columns = 1;
        }

        Point size = cellPitch;
        int rows = (source.size() + columns - 1) / columns;

        if(transpose) {
            size = size.add(flowDirection.getUnit().scale(rows, columns));
        } else {
            size = size.add(flowDirection.getUnit().scale(columns, rows));
        }
        size = size.add(cellSize);

        return new GuiElementGrid(size, source, columns, cellSize, cellPitch, flowDirection, transpose, cellAlignment);
    }

    private Point getMaxCellSize() {
        int x = 0, y = 0;

        for(GuiElement element : source) {
            Point cellSize = element.getSize();
            x = Math.max(x, cellSize.getX());
            y = Math.max(y, cellSize.getY());
        }
        return new Point(x, y);
    }
}
