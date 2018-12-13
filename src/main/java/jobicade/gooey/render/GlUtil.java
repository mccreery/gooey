package jobicade.gooey.render;

import jobicade.gooey.geom.Rect;
import jobicade.gooey.geom.Direction;
import jobicade.gooey.geom.Point;
import jobicade.gooey.render.mode.GlMode;
import jobicade.gooey.render.element.QuadBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public final class GlUtil {
	private static final Minecraft MC = Minecraft.getMinecraft();

	private GlUtil() {}

	/**
	 * All axes default to {@code scale}.
	 *
	 * @param scale The scale for all axes.
	 * @see GlStateManager#scale(float, float, float)
	 */
	public static void scale(float scale) {
		GlStateManager.scale(scale, scale, scale);
	}

	/**
	 * Draws text with black borders on all sides.
	 *
	 * @param text The text to draw.
	 * @param x The X coordinate of the text.
	 * @param y The Y coordinate of the text.
	 * @param color The color of the text.
	 */
	public static void drawBorderedString(String text, int x, int y, int color) {
		// Borders
		int border = Color.BLACK.getPacked();
		MC.fontRenderer.drawString(text, x + 1, y, border, false);
		MC.fontRenderer.drawString(text, x - 1, y, border, false);
		MC.fontRenderer.drawString(text, x, y + 1, border, false);
		MC.fontRenderer.drawString(text, x, y - 1, border, false);

		MC.fontRenderer.drawString(text, x, y, color, false);
	}

	/**
	 * Safely renders an item to the screen.
	 *
	 * @param stack The item stack to render.
	 * @param point The top left corner of the item.
	 */
	public static void renderSingleItem(ItemStack stack, Point point) {
		GlMode.push(GlMode.ITEM);
		MC.getRenderItem().renderItemAndEffectIntoGUI(stack, point.getX(), point.getY());
		GlMode.pop();
	}

	/**
	 * Renders the item with hotbar animations
	 *
	 * @param position The top left position of the item.
	 * @param stack The item stack to render.
	 * @param partialTicks The current partial ticks.
	 */
	public static void renderHotbarItem(Point position, ItemStack stack, float partialTicks) {
		if(stack.isEmpty()) return;
		float animationTicks = stack.getAnimationsToGo() - partialTicks;

		GlMode.push(GlMode.ITEM);
		if(animationTicks > 0) {
			float factor = 1 + animationTicks / 5;

			GlStateManager.pushMatrix();
			GlStateManager.translate(position.getX() + 8, position.getY() + 12, 0);
			GlStateManager.scale(1 / factor, (factor + 1) / 2, 1);
			GlStateManager.translate(-(position.getX() + 8), -(position.getY() + 12), 0.0F);

			MC.getRenderItem().renderItemAndEffectIntoGUI(MC.player, stack, position.getX(), position.getY());

			GlStateManager.popMatrix();
		} else {
			MC.getRenderItem().renderItemAndEffectIntoGUI(MC.player, stack, position.getX(), position.getY());
		}

		MC.getRenderItem().renderItemOverlays(MC.fontRenderer, stack, position.getX(), position.getY());
		GlMode.pop();
	}

	/**
	 * Draws a box styled like item tooltips.
	 *
	 * @param bounds The bounds for the box.
	 * @param zLevel The Z coordinate for the box.
	 * @see net.minecraftforge.fml.client.config.GuiUtils#drawHoveringText(ItemStack, java.util.List, int, int, int, int, int, net.minecraft.client.gui.FontRenderer)
	 */
	public static void drawTooltipBox(Rect bounds, double zLevel) {
		Color borderStart = new Color(80, 80, 0, 255);
		Color borderEnd = new Color(80, 40, 40, 127);
		Color background = new Color(183, 16, 0, 16);

		QuadBuilder builder = new QuadBuilder().setZLevel(zLevel).setColor(background);
		Rect thin = bounds.grow(-1, 0, -1, 0).withHeight(1);

		// Cross shape background
		builder.setBounds(thin).build().render();
		builder.setBounds(bounds.grow(0, -1, 0, -1)).build().render();
		builder.setBounds(thin.anchor(bounds, Direction.SOUTH, false)).build().render();

		// Borders
		GlMode.push(GlMode.OUTLINE);
		builder.setColor(d -> d.getRow() == 0 ? borderStart : borderEnd);
		builder.setBounds(bounds.grow(-1)).build().render();
		GlMode.pop();
	}

	/**
	 * Applies transformations such that the Z axis faces directly towards the player
	 * and (0, 0) is translated to above {@code entity}'s head.
	 * <p>This is similar to the method used to render player names, but any functionality can be implemented.
	 *
	 * @param entity The target entity.
	 * @param partialTicks Partial ticks.
	 * @param scaleFactor Linearly affects the size of things drawn to the billboard.
	 * @see net.minecraft.client.renderer.EntityRenderer#drawNameplate(net.minecraft.client.gui.FontRenderer, String, float, float, float, int, float, float, boolean, boolean)
	 */
	public static void setupBillboard(Entity entity, float partialTicks, float scaleFactor) {
		double dx = (entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks) - (MC.player.prevPosX + (MC.player.posX - MC.player.prevPosX) * partialTicks);
		double dy = (entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks) - (MC.player.prevPosY + (MC.player.posY - MC.player.prevPosY) * partialTicks);
		double dz = (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks) - (MC.player.prevPosZ + (MC.player.posZ - MC.player.prevPosZ) * partialTicks);

		dy += entity.height + 0.5;
		GlStateManager.translate(dx, dy, dz);

		dy -= MC.player.getEyeHeight();
		float distance = (float)Math.sqrt(dx*dx + dy*dy + dz*dz);
		scale(distance * (scaleFactor + 0.5f) / 300f);

		GlStateManager.rotate(-MC.player.rotationYaw,  0, 1, 0);
		GlStateManager.rotate(MC.player.rotationPitch, 1, 0, 0);
		GlStateManager.rotate(180, 0, 0, 1);
	}

	/**
	 * Draws a progress bar with quads.
	 *
	 * @param background The background quad to draw
	 * @param foreground The foreground quad to partially draw
	 * @param progress Index of progress between 0 and 1
	 * @param direction The direction the bar should fill up in
	 * @param rescaleTexture {@code true} to maintain the aspect ratio
	 * of the scaled foreground texture
	 */
	public static void drawProgressBar(QuadBuilder background, QuadBuilder foreground, float progress, Direction direction, boolean rescaleTexture) {
		direction = direction.mirror();
		background.build().render();

		foreground = new QuadBuilder(foreground);
		foreground.setBounds(foreground.getBounds().resize(foreground.getBounds().getSize().scale(progress, progress)).anchor(foreground.getBounds(), direction, false));

		if(rescaleTexture) {
			foreground.setTexture(foreground.getTexture().resize(foreground.getTexture().getSize().scale(progress, progress)).anchor(foreground.getTexture(), direction, false));
		}
		foreground.build().render();
	}

	/**
	 * Calculates the size of text on screen.
	 *
	 * @param string The text.
	 * @return The size of {@code string} as rendered by Minecraft's font renderer.
	 */
	public static Point getStringSize(String string) {
		return new Point(MC.fontRenderer.getStringWidth(string), MC.fontRenderer.FONT_HEIGHT);
	}

	/**
	 * Draws text to the GUI.
	 *
	 * @param string The text to render
	 * @param origin The anchor point
	 * @param alignment The alignment around {@code origin}
	 * @param color The color of the text
	 * @return The bounds of the text.
	 * @see net.minecraft.client.gui.FontRenderer#drawStringWithShadow(String, float, float, int)
	 */
	public static Rect drawString(String string, Point origin, Direction alignment, int color) {
		Rect bounds = new Rect(getStringSize(string)).align(origin, alignment);
		MC.fontRenderer.drawStringWithShadow(string, bounds.getX(), bounds.getY(), color);

		return bounds;
	}
}
