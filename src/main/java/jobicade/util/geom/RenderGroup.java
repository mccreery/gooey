package jobicade.util.geom;

import java.util.List;

import com.google.common.collect.ImmutableList;

import jobicade.util.render.GuiElement;

class RenderGroup extends GuiElement {
	private final List<GuiElement> source;

	private final Direction direction;
	private final Direction cellAlignment;

	private final Point cellSize;
	private final int pitch;

	RenderGroup(List<? extends GuiElement> source, Direction direction, Direction cellAlignment, Point cellSize, int pitch) {
		super(cellSize.shiftedBy(direction, pitch * (source.size() - 1)));

		this.source = ImmutableList.copyOf(source);
		this.direction = direction;
		this.cellAlignment = cellAlignment;
		this.cellSize = cellSize;
		this.pitch = pitch;
	}

	@Override
	public void setPosition(Point position) {
		super.setPosition(position);

		Bounds cell = new Bounds(cellSize).anchor(getBounds(), direction.mirror());
		for(GuiElement element : source) {
			element.setPosition(element.getBounds().anchor(cell, cellAlignment).getPosition());
			cell = cell.shift(direction, pitch);
		}
	}

	@Override
	public void render() {
		for(GuiElement element : source) {
			element.render();
		}
	}
}
