package jobicade.util.render;

import net.minecraft.util.math.MathHelper;

public class Color {
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color GRAY = new Color(127, 127, 127);
	public static final Color BLACK = new Color(0, 0, 0);

	public static final Color RED = new Color(255, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 0, 255);

	private final int alpha, red, green, blue;

	public Color(int red, int green, int blue) {
		this(0xff, red, green, blue);
	}

	public Color(int alpha, int red, int green, int blue) {
		this.alpha = alpha & 0xff;
		this.red = red & 0xff;
		this.green = green & 0xff;
		this.blue = blue & 0xff;
	}

	public Color(int packed) {
		this(packed >> 24, packed >> 16, packed >> 8, packed);
	}

	public Color withAlpha(int alpha) {return new Color(alpha, red, green, blue);}
	public Color withRed(int red) {return new Color(alpha, red, green, blue);}
	public Color withGreen(int green) {return new Color(alpha, red, green, blue);}
	public Color withBlue(int blue) {return new Color(alpha, red, green, blue);}

	public static Color fromHSV(float hue, float saturation, float value) {
		hue -= MathHelper.floor(hue);
		saturation = MathHelper.clamp(saturation, 0, 1);
		value = MathHelper.clamp(value, 0, 1);

		return new Color(MathHelper.hsvToRGB(hue, saturation, value)).withAlpha(255);
	}
}
