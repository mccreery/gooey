package jobicade.util;

import java.util.Random;

import jobicade.util.geom.Bounds;
import jobicade.util.geom.Point;

public class RandomWrapper {
    private final Random parent;

    public RandomWrapper(Random random) {
        this.parent = random;
	}

	public Random getRandom() {
		return parent;
	}

    /** @param min The low end of the range (inclusive)
	 * @param max The high end of the range (exclusive)
	 * @return A random integer between the specified values
	 * @see Random#nextInt(int) */
	public int nextInt(int min, int max) {
		int bound = max - min;
		return min + (bound >= 0 ? parent.nextInt(bound) : -parent.nextInt(-bound));
	}

	/** @param min The low end of the range (inclusive)
	 * @param max The high end of the range (exclusive)
	 * @return A random float between the specified values
	 * @see Random#nextFloat() */
	public float nextFloat(float min, float max) {
		return min + parent.nextFloat() * (max - min);
	}

	/** @param min The low end of the range (inclusive)
	 * @param max The high end of the range (exclusive)
	 * @return A random double between the specified values
	 * @see Random#nextDouble() */
	public double nextDouble(double min, double max) {
		return min + parent.nextDouble() * (max - min);
	}

	/** @param bounds The range
	 * @return A random point within the bounds */
	public Point nextPoint(Bounds bounds) {
		return new Point(
			nextInt(bounds.getLeft(), bounds.getRight()),
			nextInt(bounds.getTop(), bounds.getBottom()));
	}

	/** @param probability The chance to return {@code true}
	 * @return {@code true} with a {@code probability} chance */
	public boolean nextBoolean(double probability) {
		return parent.nextFloat() < probability;
	}
}
