package jobicade.gooey.render.element;

import jobicade.gooey.geom.Rect;
import jobicade.gooey.geom.Point;

public abstract class GuiElement {
	private final Point size;
	private Rect bounds;

	public GuiElement(Point size) {
		this.size = size;
		this.bounds = new Rect(size);
	}

	public GuiElement(Rect bounds) {
		this.size = bounds.getSize();
		this.bounds = bounds;
	}

	public final Point getSize() {
		return size;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setPosition(Point position) {
		bounds = bounds.move(position);
	}

	public abstract void render();
}
