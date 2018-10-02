package jobicade.util.mode;

import static jobicade.util.Constants.ICONS;
import static jobicade.util.Constants.MC;

import net.minecraft.util.ResourceLocation;

public class TextureMode extends GlMode {
	private final ResourceLocation texture;

	public TextureMode(ResourceLocation texture) {
		this.texture = texture;
	}

	@Override
	public void begin() {
		MC.getTextureManager().bindTexture(texture);
	}

	@Override
	public void end() {
		MC.getTextureManager().bindTexture(ICONS);
	}
}
