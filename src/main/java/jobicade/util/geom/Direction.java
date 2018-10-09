package jobicade.util.geom;

public enum Direction {
	NORTH_WEST(0, 0),
	NORTH(0, 1),
	NORTH_EAST(0, 2),
	WEST(1, 0),
	CENTER(1, 1),
	EAST(1, 2),
	SOUTH_WEST(2, 0),
	SOUTH(2, 1),
	SOUTH_EAST(2, 2);

	private final int row, col;
	private static Direction[][] grid = new Direction[3][3];

	static {
		for(Direction d : values()) {
			grid[d.row][d.col] = d;
		}
	}

	Direction(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Point getUnit() {
		return new Point(col - 1, row - 1);
	}

	public static Direction get(int row, int col) {
		return grid[row][col];
	}

	public int getRow() { return row; }
	public int getCol() { return col; }
	public Direction withRow(int row) { return get(row, col); }
	public Direction withCol(int col) { return get(row, col); }

	public Direction mirrorRow() { return get(2 - row, col); }
	public Direction mirrorCol() { return get(row, 2 - col); }
	public Direction mirror() { return get(2 - row, 2 - col); }
}
