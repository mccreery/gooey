package jobicade.util.render;

import jobicade.util.geom.Bounds;
import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;

public abstract class Renderable {
	public abstract Point getSize();
	protected abstract void renderUnsafe(Bounds bounds, Direction contentAlignment);

	public boolean shouldRender() {return true;}

	public final void render(Bounds bounds, Direction contentAlignment) {
		if(shouldRender()) {
			if(!bounds.getSize().equals(getSize())) {
				throw new IllegalArgumentException("Bounds size does not equal size");
			}
			renderUnsafe(bounds, contentAlignment);
		}
	}
}
