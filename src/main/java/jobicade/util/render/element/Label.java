package jobicade.util.render.element;

import jobicade.util.geom.Rectangle;
import jobicade.util.geom.Point;
import jobicade.util.render.Color;
import net.minecraft.client.Minecraft;

public class Label extends GuiElement {
    private final String text;

    private boolean dropShadow;
    private Color color;

    public Label(String text) {
        super(new Point(Minecraft.getMinecraft().fontRenderer.getStringWidth(text), Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT));
        this.text = text;
    }

    public boolean getDropShadow() {
        return dropShadow;
    }

    public Label setDropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Label setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void render() {
        Rectangle bounds = getBounds();
        Minecraft.getMinecraft().fontRenderer.drawString(text, bounds.getX(), bounds.getY(), color.getPacked(), dropShadow);
    }
}
