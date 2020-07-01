package jobicade.gooey;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

public final class GooeyMath {
    private GooeyMath() {}

    public static int lerp(int a, int b, float t) {
        return Math.round(a * (1.0f - t) + b * t);
    }

    public static float lerp(float a, float b, float t) {
        return a * (1.0f - t) + b * t;
    }

    public static float floorMod(float x, float y) {
        return (float)(x - Math.floor(x / y) * y);
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

    public static int clamp(Range<Integer> range, int x) {
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Empty range");
        }

        if (range.hasLowerBound()) {
            return Math.max(x, lowerBound(range));
        } else if (range.hasUpperBound()) {
            return Math.min(x, upperBound(range));
        } else {
            return x;
        }
    }

    public static int lowerBound(Range<Integer> range) {
        if (range.lowerBoundType() == BoundType.OPEN) {
            return range.lowerEndpoint() + 1;
        } else {
            return range.lowerEndpoint();
        }
    }

    public static int upperBound(Range<Integer> range) {
        if (range.upperBoundType() == BoundType.OPEN) {
            return range.upperEndpoint() - 1;
        } else {
            return range.upperEndpoint();
        }
    }
}
