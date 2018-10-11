package jobicade.util.render;

import static jobicade.util.Constants.MC;

import jobicade.util.geom.Rectangle;
import jobicade.util.geom.Direction;
import jobicade.util.geom.Point;
import jobicade.util.render.mode.GlMode;
import jobicade.util.render.element.QuadBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public final class GlUtil {
	private GlUtil() {}

	/** All axes default to {@code scale}
	 * @see GlStateManager#scale(float, float, float) */
	public static void scale(float scale) {
		GlStateManager.scale(scale, scale, scale);
	}

	/** Draws text with black borders on all sides */
	public static void drawBorderedString(String text, int x, int y, int color) {
		// Borders
		int border = Color.BLACK.getPacked();
		MC.fontRenderer.drawString(text, x + 1, y, border, false);
		MC.fontRenderer.drawString(text, x - 1, y, border, false);
		MC.fontRenderer.drawString(text, x, y + 1, border, false);
		MC.fontRenderer.drawString(text, x, y - 1, border, false);

		MC.fontRenderer.drawString(text, x, y, color, false);
	}

	/** @see #renderSingleItem(ItemStack, int, int) */
	public static void renderSingleItem(ItemStack stack, Point point) {
		renderSingleItem(stack, point.getX(), point.getY());
	}

	/** Renders {@code stack} to the GUI, and reverts lighting side effects
	 *
	 * @see RenderHelper#enableGUIStandardItemLighting()
	 * @see net.minecraft.client.renderer.RenderItem#renderItemAndEffectIntoGUI(ItemStack, int, int)
	 * @see RenderHelper#disableStandardItemLighting() */
	public static void renderSingleItem(ItemStack stack, int x, int y) {
		GlMode.push(GlMode.ITEM);
		MC.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
		GlMode.pop();
	}

	/** Renders the item with hotbar animations */
	public static void renderHotbarItem(Rectangle bounds, ItemStack stack, float partialTicks) {
		if(stack.isEmpty()) return;
		float animationTicks = stack.getAnimationsToGo() - partialTicks;

		GlMode.push(GlMode.ITEM);
		if(animationTicks > 0) {
			float factor = 1 + animationTicks / 5;

			GlStateManager.pushMatrix();
			GlStateManager.translate(bounds.getX() + 8, bounds.getY() + 12, 0);
			GlStateManager.scale(1 / factor, (factor + 1) / 2, 1);
			GlStateManager.translate(-(bounds.getX() + 8), -(bounds.getY() + 12), 0.0F);

			MC.getRenderItem().renderItemAndEffectIntoGUI(MC.player, stack, bounds.getX(), bounds.getY());

			GlStateManager.popMatrix();
		} else {
			MC.getRenderItem().renderItemAndEffectIntoGUI(MC.player, stack, bounds.getX(), bounds.getY());
		}

		MC.getRenderItem().renderItemOverlays(MC.fontRenderer, stack, bounds.getX(), bounds.getY());
		GlMode.pop();
	}

	/** @see GuiUtils#drawHoveringText(ItemStack, List, int, int, int, int, int, net.minecraft.client.gui.FontRenderer) */
	public static void drawTooltipBox(Rectangle bounds, double zLevel) {
		Color borderStart = new Color(80, 80, 0, 255);
		Color borderEnd = new Color(80, 40, 40, 127);
		Color background = new Color(183, 16, 0, 16);

		QuadBuilder builder = new QuadBuilder().setZLevel(zLevel).setColor(background);
		Rectangle thin = bounds.grow(-1, 0, -1, 0).withHeight(1);

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

	/** Applies transformations such that the Z axis faces directly towards the player
	 * and (0, 0) is translated to above {@code entity}'s head.
	 * <p>This is similar to the method used to render player names, but any functionality can be implemented
	 *
	 * @param scaleFactor Linearly affects the size of things drawn to the billboard
	 * @see net.minecraft.client.renderer.EntityRenderer#drawNameplate(net.minecraft.client.gui.FontRenderer, String, float, float, float, int, float, float, boolean, boolean) */
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

		/** Draws a progress bar with textures
	 * @param progress Index of progress between 0 and 1
	 * @param direction The direction the bar should fill up in */
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

	/** @return The size of {@code string} as rendered by Minecraft's font renderer */
	public static Point getStringSize(String string) {
		return new Point(MC.fontRenderer.getStringWidth(string), MC.fontRenderer.FONT_HEIGHT);
	}

	/** @param origin The anchor point
	 * @param alignment The alignment around {@code origin}
	 * @see net.minecraft.client.gui.FontRenderer#drawStringWithShadow(String, float, float, int) */
	public static Rectangle drawString(String string, Point origin, Direction alignment, int color) {
		Rectangle bounds = Rectangle.fromPositionSize(Point.zero(), getStringSize(string)).align(origin, alignment);
		MC.fontRenderer.drawStringWithShadow(string, bounds.getX(), bounds.getY(), color);

		return bounds;
	}
}
