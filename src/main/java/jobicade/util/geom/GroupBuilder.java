package jobicade.util.geom;

import java.util.List;

import jobicade.util.render.GuiElement;

public class GroupBuilder<T extends GuiElement> {
    private List<T> source;

	private Direction direction = Direction.EAST;
	private Direction cellAlignment = Direction.CENTER;

	private int minPitch;
    private int minGutter;

    public GroupBuilder(List<T> source) {
        this.source = source;
    }

    public List<T> getSource() {
        return source;
    }

    public GroupBuilder<T> setSource(List<T> source) {
        this.source = source;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public GroupBuilder<T> setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public Direction getCellAlignment() {
        return cellAlignment;
    }

    public GroupBuilder<T> setCellAlignment(Direction cellAlignment) {
        this.cellAlignment = cellAlignment;
        return this;
    }

    public int getMinPitch() {
        return minPitch;
    }

    public GroupBuilder<T> setMinPitch(int minPitch) {
        this.minPitch = minPitch;
        return this;
    }

    public int getMinGutter() {
        return minGutter;
    }

    public GroupBuilder<T> setMinGutter(int minGutter) {
        this.minGutter = minGutter;
        return this;
    }

    public GuiElement build() {
        Point cellSize = getMaxCellSize();
        int pitch = getPitch(cellSize, direction, minPitch, minGutter);

        return new RenderGroup(source, direction, cellAlignment, cellSize, pitch);
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

    private static int getPitch(Point cellSize, Direction direction, int minPitch, int minGutter) {
		int cell = direction.getCol() == 1 ? cellSize.getY() : cellSize.getX();
		return Math.max(cell + minGutter, minPitch);
	}
}
