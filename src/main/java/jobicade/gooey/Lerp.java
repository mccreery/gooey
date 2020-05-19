package jobicade.gooey;

public final class Lerp {
    private Lerp() {}

    public static int lerp(int a, int b, float t) {
        return Math.round(a * (1.0f - t) + b * t);
    }

    public static float lerp(float a, float b, float t) {
        return a * (1.0f - t) + b * t;
    }

    public static int clamp(int x, int min, int max) {
        if (x < min) {
            return min;
        } else if(x > max) {
            return max;
        } else {
            return x;
        }
    }

    public static float clamp(float x) {
        return clamp(x, 0.0f, 1.0f);
    }

    public static float clamp(float x, float min, float max) {
        if (x < min) {
            return min;
        } else if(x > max) {
            return max;
        } else {
            return x;
        }
    }

    public static float floorMod(float x, float y) {
        return (float)(x - Math.floor(x / y) * y);
    }
}
