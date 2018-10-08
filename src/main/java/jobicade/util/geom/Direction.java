package jobicade.util.geom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;

/** One of 8 cardinal directions or {@link CENTER}, the null direction */
public enum Direction {
	NORTH_WEST("northWest"),
	NORTH("north"),
	NORTH_EAST("northEast"),

	WEST ("west"),
	CENTER("center"),
	EAST ("east"),

	SOUTH_WEST("southWest"),
	SOUTH("south"),
	SOUTH_EAST("southEast");

	public enum Options implements Function<Direction, Direction> {
		ALL((1 << Direction.values().length) - 1) {
			@Override
			public Direction apply(Direction direction) {
				return direction != null ? direction : NORTH_WEST;
			}
		},
		CORNERS(NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST) {
			@Override
			public Direction apply(Direction direction) {
				if(direction != null) {
					return fromRowColumn(direction.getRow() > 1 ? 2 : 0, direction.getColumn() > 1 ? 2 : 0);
				} else {
					return NORTH_WEST;
				}
			}
		},
		SIDES(NORTH, EAST, SOUTH, WEST) {
			@Override
			public Direction apply(Direction direction) {
				if(direction == null || direction == CENTER) {
					return NORTH;
				} else if(direction.getRow() == 1) {
					return direction;
				} else {
					return direction.withColumn(1);
				}
			}
		},
		WEST_EAST(WEST, EAST) {
			@Override
			public Direction apply(Direction direction) {
				return direction != null && direction.getColumn() >= 2 ? EAST : WEST;
			}
		},
		NORTH_SOUTH(NORTH, SOUTH) {
			@Override
			public Direction apply(Direction direction) {
				return direction != null && direction.getRow() >= 2 ? SOUTH : NORTH;
			}
		},
		HORIZONTAL(WEST, CENTER, EAST) {
			@Override
			public Direction apply(Direction direction) {
				return direction != null ? direction.withRow(1) : WEST;
			}
		},
		VERTICAL(NORTH, CENTER, SOUTH) {
			@Override
			public Direction apply(Direction direction) {
				return direction != null ? direction.withColumn(1) : NORTH;
			}
		},
		I(NORTH_WEST, NORTH, NORTH_EAST, CENTER, SOUTH_WEST, SOUTH, SOUTH_EAST) {
			@Override
			public Direction apply(Direction direction) {
				switch(direction) {
					case WEST: return NORTH_WEST;
					case EAST: return NORTH_EAST;
					default:   return direction != null ? direction : NORTH_WEST;
				}
			}
		},
		BAR(NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH, SOUTH_EAST) {
			@Override
			public Direction apply(Direction direction) {
				if(direction == SOUTH) {
					return direction;
				} else {
					return CORNERS.apply(direction);
				}
			}
		},
		X(NORTH_WEST, NORTH_EAST, CENTER, SOUTH_WEST, SOUTH_EAST) {
			@Override
			public Direction apply(Direction direction) {
				return direction == CENTER ? direction : CORNERS.apply(direction);
			}
		},
		TOP_BOTTOM(NORTH_WEST, NORTH, NORTH_EAST, SOUTH_WEST, SOUTH, SOUTH_EAST) {
			@Override
			public Direction apply(Direction direction) {
				if(direction == null) {
					return NORTH_WEST;
				} else if(direction.getRow() == 1) {
					return direction.withRow(0);
				} else {
					return direction;
				}
			}
		},
		LEFT_RIGHT(NORTH_WEST, WEST, SOUTH_WEST, NORTH_EAST, EAST, SOUTH_EAST) {
			@Override
			public Direction apply(Direction direction) {
				if(direction == null) {
					return NORTH_WEST;
				} else if(direction.getColumn() == 1) {
					return direction.withColumn(0);
				} else {
					return direction;
				}
			}
		},
		NONE() {
			@Override
			public Direction apply(Direction direction) {
				return null;
			}
		};

		private final List<Direction> directions;
		private final int flags;

		Options(Direction... directions) {
			this.directions = Collections.unmodifiableList(Arrays.asList(directions));
			flags = getFlags(directions);
		}

		Options(int flags) {
			List<Direction> directions = new ArrayList<>(Direction.values().length);
			for(Direction direction : Direction.values()) {
				if(direction.in(flags)) directions.add(direction);
			}

			this.directions = Collections.unmodifiableList(directions);
			this.flags = flags;
		}

		public List<Direction> getDirections() {
			return directions;
		}

		public boolean isValid(Direction direction) {
			return in(direction, flags);
		}
	}

	private final String name;

	Direction(String name) {
		this.name = name;
	}

	public String getUnlocalizedName() {
		return "direction." + name;
	}

	public String getLocalizedName() {
		return I18n.format(getUnlocalizedName());
	}

	public int getRow() {return ordinal() / 3;}
	public int getColumn() {return ordinal() % 3;}

	public Direction withColumn(int column) {return fromRowColumn(getRow(), column);}
	public Direction withRow(int row) {return fromRowColumn(row, getColumn());}

	public Direction mirrorRow() {return fromRowColumn(2 - getRow(), getColumn());}
	public Direction mirrorColumn() {return fromRowColumn(getRow(), 2 - getColumn());}
	public Direction mirror() {return fromRowColumn(2 - getRow(), 2 - getColumn());}

	/** @see #in(int) */
	private static boolean in(Direction direction, int flags) {
		return direction != null && direction.in(flags);
	}

	private boolean in(int flags) {
		return (flags & getFlag()) != 0;
	}

	private int getFlag() {
		return 1 << ordinal();
	}

	private static int getFlags(Direction... directions) {
		int flags = 0;

		for(Direction direction : directions) {
			flags |= direction.getFlag();
		}
		return flags;
	}

	public Point getRowColumn() {
		return new Point(getColumn(), getRow());
	}
	public static Direction fromRowColumn(Point rowColumn) {
		return fromRowColumn(rowColumn.getY(), rowColumn.getX());
	}
	public static Direction fromRowColumn(int row, int column) {
		return values()[row * 3 + column];
	}

	public static String toString(Direction direction) {
		return direction != null ? direction.toString() : "";
	}

	@Override
	public String toString() {
		return name;
	}

	public static Direction fromString(String name) {
		for(Direction direction : values()) {
			if(direction.name.equalsIgnoreCase(name)) return direction;
		}
		return null;
	}
}
