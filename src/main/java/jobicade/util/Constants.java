package jobicade.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class Constants {
    public static final int SPACER = 5;

    public static final Minecraft MC = Minecraft.getMinecraft();

    public static final ResourceLocation ICONS = Gui.ICONS;
    public static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    public static final ResourceLocation PARTICLES = new ResourceLocation("textures/particle/particles.png");
}
