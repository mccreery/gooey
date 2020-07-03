package jobicade.gooey;

public final class Color {
	public static final Color WHITE = fromRgb(255, 255, 255);
	public static final Color GRAY = fromRgb(127, 127, 127);
	public static final Color BLACK = fromRgb(0, 0, 0);

	public static final Color RED = fromRgb(255, 0, 0);
	public static final Color GREEN = fromRgb(0, 255, 0);
	public static final Color BLUE = fromRgb(0, 0, 255);

	private final byte red, green, blue, alpha;

	private Color(int red, int green, int blue, int alpha) {
		this.alpha = (byte)GooeyMath.clamp(alpha, 0, 255);
		this.red = (byte)GooeyMath.clamp(red, 0, 255);
		this.green = (byte)GooeyMath.clamp(green, 0, 255);
		this.blue = (byte)GooeyMath.clamp(blue, 0, 255);
	}

	public static Color fromRgb(int red, int green, int blue) {
		return new Color(red, green, blue, 255);
	}

	public static Color fromRgba(int red, int green, int blue, int alpha) {
		return new Color(red, green, blue, alpha);
	}

	public static final Color fromArgb(int alpha, int red, int green, int blue) {
		return new Color(red, green, blue, alpha);
	}

	public int getRed() { return red; }
	public int getGreen() { return green; }
	public int getBlue() { return blue; }
	public int getAlpha() { return alpha; }

	public Color withRed(int red) { return new Color(red, green, blue, alpha); }
	public Color withGreen(int green) { return new Color(red, green, blue, alpha); }
	public Color withBlue(int blue) { return new Color(red, green, blue, alpha); }
	public Color withAlpha(int alpha) { return new Color(red, green, blue, alpha); }

	public int packRgb() {
		return (red << 16) | (green << 8) | blue;
	}

	public int packRgba() {
		return (red << 24) | (green << 16) | (blue << 8) | alpha;
	}

	public int packArgb() {
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	private static Color fromRgbF(float red, float green, float blue) {
		return new Color(Math.round(red * 255.0f), Math.round(green * 255.0f), Math.round(blue * 255.0f), 255);
	}

	public static Color fromHsv(float hue, float saturation, float value) {
		hue = GooeyMath.floorMod(hue, 360.0f);
		saturation = GooeyMath.clamp(saturation);
		value = GooeyMath.clamp(value);

		// floorMod does not need to be used where both num and denom positive
		float t = (hue / 60.0f) % 1.0f;
		float low = value * (1 - saturation);

		// Each 60 degree arc is handled separately
		if (hue < 60.0f) {
			return fromRgbF(value, GooeyMath.lerp(low, value, t), low);
		} else if (hue < 120.0f) {
			return fromRgbF(GooeyMath.lerp(value, low, t), value, low);
		} else if (hue < 180.0f) {
			return fromRgbF(low, value, GooeyMath.lerp(low, value, t));
		} else if (hue < 240.0f) {
			return fromRgbF(low, GooeyMath.lerp(value, low, t), value);
		} else if (hue < 300.0f) {
			return fromRgbF(GooeyMath.lerp(low, value, t), low, value);
		} else {
			return fromRgbF(value, low, GooeyMath.lerp(value, low, t));
		}
	}
}
