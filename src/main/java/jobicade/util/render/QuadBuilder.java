package jobicade.util.render;

import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import jobicade.util.geom.Bounds;
import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class QuadBuilder {
    private static final float TEX_SCALE = 1f / 256f;

    private double zLevel;

    private Bounds bounds, texture;
    private Function<Direction, Color> colorFunction;

    public QuadBuilder() {}

    public QuadBuilder(QuadBuilder builder) {
        zLevel = builder.zLevel;
        bounds = builder.bounds;
        texture = builder.texture;
        colorFunction = builder.colorFunction;
    }

    public double getZLevel() {return zLevel;}

    public QuadBuilder setZLevel(double zLevel) {
        this.zLevel = zLevel;
        return this;
    }

    public Bounds getBounds() {return bounds;}

    public QuadBuilder setBounds(Bounds bounds) {
        this.bounds = bounds;
        return this;
    }

    public Bounds getTexture() {return texture;}

    public QuadBuilder setTexture(Bounds texture) {
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

    public void render() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        VertexFormat format = new VertexFormat();
        if(bounds != null) format.addElement(DefaultVertexFormats.POSITION_3F);
        if(texture != null) format.addElement(DefaultVertexFormats.TEX_2F);
        if(colorFunction != null) format.addElement(DefaultVertexFormats.COLOR_4UB);

        builder.begin(GL11.GL_QUADS, format);
        addVertex(builder, Direction.SOUTH_WEST);
        addVertex(builder, Direction.SOUTH_EAST);
        addVertex(builder, Direction.NORTH_EAST);
        addVertex(builder, Direction.NORTH_WEST);
        tessellator.draw();
    }

    private void addVertex(BufferBuilder builder, Direction anchor) {
        for(VertexFormatElement element : builder.getVertexFormat().getElements()) {
            switch(element.getUsage()) {
                case POSITION: {
                    Point xy = bounds.getAnchor(anchor);
                    builder.pos(xy.getX(), xy.getY(), zLevel);
                    break;
                }
                case UV: {
                    Point uv = texture.getAnchor(anchor);
                    builder.tex(uv.getX() * TEX_SCALE, uv.getY() * TEX_SCALE);
                    break;
                }
                case COLOR: {
                    Color color = colorFunction.apply(anchor);
                    builder.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                    break;
                }
                default: throw new IllegalStateException("Unsupported builder element");
            }
        }
        builder.endVertex();
    }
}
