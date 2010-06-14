package model.production;

import model.warehouse.Position;

public enum Direction {
	NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, 1);

	private final Position associatedPosition;

	Direction(int row, int col) {
		this(new Position(row, col));
	}

	Direction(Position associatedPosition) {
		this.associatedPosition = associatedPosition;
	}

	public Position getAssociatedPosition() {
		return associatedPosition;
	}
}
