package jobicade.util.render;

import jobicade.util.geom.Bounds;
import jobicade.util.geom.Point;

public abstract class GuiElement {
	private final Point size;
	private Bounds bounds;

	public GuiElement(Point size) {
		this.size = size;
		this.bounds = new Bounds(size);
	}

	public GuiElement(Bounds bounds) {
		this.size = bounds.getSize();
		this.bounds = bounds;
	}

	public final Point getSize() {
		return size;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setPosition(Point position) {
		bounds = bounds.withPosition(position);
	}

	public abstract void render();
}
