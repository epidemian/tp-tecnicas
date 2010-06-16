package model.production;

import static java.lang.Math.PI;
import model.exception.BusinessLogicException;
import model.warehouse.Position;

public enum Direction {
	NORTH(-1, 0, 'N', 0), EAST(0, 1, 'E', PI * 0.5), SOUTH(1, 0, 'S', PI), WEST(
			0, -1, 'W', PI * 1.5);

	private final Position position;
	private final char symbol;
	private final double rotation;

	Direction(int row, int col, char symbol, double rotation) {
		this(new Position(row, col), symbol, rotation);
	}

	Direction(Position position, char symbol, double rotation) {
		this.position = position;
		this.symbol = symbol;
		this.rotation = rotation;
	}

	public Position getAssociatedPosition() {
		return position;
	}

	public char getSymbol() {
		return symbol;
	}

	public double getAssociatedRotation() {
		return rotation;
	}

	public static Direction getDirectionByPosition(Position position) {
		for (Direction dir : Direction.values())
			if (dir.getAssociatedPosition().equals(position))
				return dir;
		throw new BusinessLogicException(
				"No direction with associated position: " + position);
	}
}
