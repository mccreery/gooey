package jobicade.util.render.element;

import java.util.function.Function;

import org.apache.commons.lang3.builder.Builder;

import jobicade.util.geom.Rect;
import jobicade.util.geom.Direction;
import jobicade.util.render.Color;

public class QuadBuilder implements Builder<GuiElement> {
    private double zLevel;
    private Rect bounds;
    private Rect texture;
    private Function<Direction, Color> colorFunction;

    public QuadBuilder() {}

    public QuadBuilder(QuadBuilder builder) {
        this.zLevel = builder.zLevel;
        this.bounds = builder.bounds;
        this.texture = builder.texture;
        this.colorFunction = builder.colorFunction;
    }

    public double getZLevel() {return zLevel;}

    public QuadBuilder setZLevel(double zLevel) {
        this.zLevel = zLevel;
        return this;
    }

    public Rect getBounds() {return bounds;}

    public QuadBuilder setBounds(Rect bounds) {
        this.bounds = bounds;
        return this;
    }

    public Rect getTexture() {return texture;}

    public QuadBuilder setTexture(Rect texture) {
        this.texture = texture;
        return this;
    }

    public Color getColor(Direction anchor) {
        return colorFunction.apply(anchor);
    }

    public QuadBuilder setColor(Color color) {
        return setColor(d -> color);
    }

    public QuadBuilder setColor(Function<Direction, Color> colorFunction) {
        this.colorFunction = colorFunction;
        return this;
    }

    @Override
    public GuiElement build() {
        return new Quad(bounds, zLevel, texture, colorFunction);
    }
}
