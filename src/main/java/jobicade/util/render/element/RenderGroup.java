package jobicade.util.render.element;

import java.util.List;

import com.google.common.collect.ImmutableList;

import jobicade.util.geom.Rectangle;
import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;

class RenderGroup extends GuiElement {
	private final List<GuiElement> source;

	private final Direction direction;
	private final Direction cellAlignment;

	private final Point cellSize;
	private final int pitch;

	RenderGroup(List<? extends GuiElement> source, Direction direction, Direction cellAlignment, Point cellSize, int pitch) {
		super(cellSize.add(direction, pitch * (source.size() - 1)));

		this.source = ImmutableList.copyOf(source);
		this.direction = direction;
		this.cellAlignment = cellAlignment;
		this.cellSize = cellSize;
		this.pitch = pitch;
	}

	@Override
	public void setPosition(Point position) {
		super.setPosition(position);

		Rectangle cell = Rectangle.fromPositionSize(Point.zero(), cellSize).anchor(getBounds(), direction.mirror(), false);
		for(GuiElement element : source) {
			element.setPosition(element.getBounds().anchor(cell, cellAlignment, false).getPosition());
			cell = cell.translate(direction, pitch);
		}
	}

	@Override
	public void render() {
		for(GuiElement element : source) {
			element.render();
		}
	}
}
