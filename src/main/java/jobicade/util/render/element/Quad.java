package jobicade.util.render.element;

import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import jobicade.util.geom.Rectangle;
import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;
import jobicade.util.render.Color;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

class Quad extends GuiElement {
    private static final float TEX_SCALE = 1f / 256f;

    private final double zLevel;
    private final Rectangle texture;
    private final Function<Direction, Color> colorFunction;

    public Quad(Rectangle bounds, double zLevel, Rectangle texture, Function<Direction, Color> colorFunction) {
        super(bounds);

        this.zLevel = zLevel;
        this.texture = texture;
        this.colorFunction = colorFunction;
    }

    @Override
    public void render() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        VertexFormat format = new VertexFormat();
        format.addElement(DefaultVertexFormats.POSITION_3F);

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
                    Point xy = getBounds().getAnchor(anchor);
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
