package jobicade.gooey.render.element;

import java.util.ArrayList;
import java.util.List;

import jobicade.gooey.geom.Direction;
import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

class GuiElementGrid extends GuiElement {
	private final List<GuiElement> source;
	private final int columns;
	private final Point cellSize;
	private final Point cellPitch;

	private final Direction flowDirection;
	private final boolean transpose;
	private final Direction cellAlignment;

	GuiElementGrid(Point size, List<? extends GuiElement> source, int columns, Point cellSize, Point cellPitch,
			Direction flowDirection, boolean transpose, Direction cellAlignment) {
		super(size);
		this.source = new ArrayList<>(source);
		this.columns = columns;
		this.cellSize = cellSize;
		this.cellPitch = cellPitch;
		this.flowDirection = flowDirection;
		this.transpose = transpose;
		this.cellAlignment = cellAlignment;
	}

	@Override
	public void render() {
		Rect cell = new Rect(cellSize).anchor(getBounds(), flowDirection.mirror(), false);

		Point rowOffset = cellPitch.scale(flowDirection.withCol(1).getUnit());
		Point colOffset = cellPitch.scale(flowDirection.withRow(1).getUnit());

		if(transpose) {
			Point temp = rowOffset;
			rowOffset = colOffset;
			colOffset = temp;
		}

		for(int i = 0; i < source.size(); ) {
			Rect rowStart = cell;

			for(int j = 0; i < source.size() && j < columns; i++, j++) {
				GuiElement element = source.get(i);
				element.setPosition(element.getBounds().anchor(cell, cellAlignment, false).getPosition());
				element.render();

				cell = cell.translate(colOffset);
			}
			cell = rowStart.translate(rowOffset);
		}
	}
}
