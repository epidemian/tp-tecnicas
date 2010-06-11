package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.exception.BusinessLogicException;
import model.warehouse.TileElementVisitor;

public class Conveyor extends ProductionLineElement {

	public enum Direction {
		NORTH, EAST, SOUTH, WEST;
	}

	private final Direction previousLineElementDirection;
	private final Direction nextLineElementDirection;

	public Conveyor() {
		this(Direction.WEST, Direction.EAST);
	}
	
	public Conveyor(Direction prevLineElementDirection,
			Direction nextLineElementDirection) {
		super(1, 1);
		
		checkNotNull(prevLineElementDirection,
				"previous line element direction");
		checkNotNull(nextLineElementDirection, "next line element direction");
		if (prevLineElementDirection == nextLineElementDirection)
			throw new BusinessLogicException(
					"previous and next line element directions must be different");
		
		this.previousLineElementDirection = prevLineElementDirection;
		this.nextLineElementDirection = nextLineElementDirection;
	}

	public Direction getPreviousLineElementDirection() {
		return previousLineElementDirection;
	}

	public Direction getNextLineElementDirection() {
		return nextLineElementDirection;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitConveyor(this);
	}

	@Override
	public String toString() {
		return "Conveyor";
	}
}
