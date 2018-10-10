package jobicade.util.render.element;

import jobicade.util.geom.Rectangle;
import jobicade.util.geom.Point;

public abstract class GuiElement {
	private final Point size;
	private Rectangle bounds;

	public GuiElement(Point size) {
		this.size = size;
		this.bounds = Rectangle.fromPositionSize(Point.zero(), size);
	}

	public GuiElement(Rectangle bounds) {
		this.size = bounds.getSize();
		this.bounds = bounds;
	}

	public final Point getSize() {
		return size;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setPosition(Point position) {
		bounds = bounds.move(position);
	}

	public abstract void render();
}
