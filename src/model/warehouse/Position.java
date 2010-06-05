package model.warehouse;

/**
 * An immutable class representing a coordinate in a
 * 2-dimensional-discrete-world.
 */
public class Position implements Cloneable {

	public static final Position ZERO = new Position();
	
	private final int row;
	private final int col;

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Position() {
		this(0, 0);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public Position add(Position other) {
		return new Position(this.row + other.row, this.col + other.col);
	}

	@Override
	public Position clone() {
		return new Position(this.row, this.col);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
