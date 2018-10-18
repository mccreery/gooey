package jobicade.util.render.element;

import java.util.List;

import com.google.common.collect.ImmutableList;

import jobicade.util.geom.Rectangle;
import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;

class RenderGroup extends GuiElement {
	private final List<GuiElement> source;
	private final int columns;
	private final Point cellSize;
	private final Point cellPitch;

	private final Direction flowDirection;
	private final boolean transpose;
	private final Direction cellAlignment;

	RenderGroup(Point size, List<? extends GuiElement> source, int columns, Point cellSize, Point cellPitch,
			Direction flowDirection, boolean transpose, Direction cellAlignment) {
		super(size);
		this.source = ImmutableList.copyOf(source);
		this.columns = columns;
		this.cellSize = cellSize;
		this.cellPitch = cellPitch;
		this.flowDirection = flowDirection;
		this.transpose = transpose;
		this.cellAlignment = cellAlignment;
	}

	@Override
	public void setPosition(Point position) {
		super.setPosition(position);

		Rectangle cell = Rectangle.fromPositionSize(Point.zero(), cellSize)
			.anchor(getBounds(), flowDirection.mirror(), false);

		Point rowOffset = cellPitch.scale(flowDirection.withCol(1).getUnit());
		Point colOffset = cellPitch.scale(flowDirection.withRow(1).getUnit());

		if(transpose) {
			Point temp = rowOffset;
			rowOffset = colOffset;
			colOffset = temp;
		}

		for(int i = 0; i < source.size(); ) {
			Rectangle rowStart = cell;

			for(int j = 0; i < source.size() && j < columns; i++, j++) {
				GuiElement element = source.get(i);
				element.setPosition(element.getBounds().anchor(cell, cellAlignment, false).getPosition());
				cell = cell.translate(colOffset);
			}
			cell = rowStart.translate(rowOffset);
		}
	}

	@Override
	public void render() {
		for(GuiElement element : source) {
			element.render();
		}
	}
}
