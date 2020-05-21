package jobicade.gooey.layout;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import jobicade.gooey.geom.Point;
import jobicade.gooey.geom.Rect;

/**
 * Tracks the position and size of a movable rectangular object.
 */
public class Layout {
    protected Rect bounds;

    private final Point initialSize;
    private final Set<Flag> flags;

    public enum Flag {
        SHRINK_X, SHRINK_Y,
        GROW_X, GROW_Y
    }

    public Layout(Point size, Flag... flags) {
        this.bounds = Rect.bySize(Point.ZERO, size);

        this.initialSize = size;
        this.flags = EnumSet.noneOf(Flag.class);
        Collections.addAll(this.flags, flags);
    }

    public void resize(Point size) {
        if (size.getX() < initialSize.getX() && flags.contains(Flag.SHRINK_X)
                || size.getX() > initialSize.getX() && flags.contains(Flag.GROW_X)) {
            bounds = bounds.withWidth(size.getX());
        } else {
            bounds = bounds.withWidth(initialSize.getX());
        }

        if (size.getY() < initialSize.getY() && flags.contains(Flag.SHRINK_Y)
                || size.getY() > initialSize.getY() && flags.contains(Flag.GROW_Y)) {
            bounds = bounds.withHeight(size.getY());
        } else {
            bounds = bounds.withHeight(initialSize.getY());
        }
    }

    public void move(Point position) {
        bounds = bounds.withPosition(position);
    }

    public Rect getBounds() {
        return bounds;
    }
}
