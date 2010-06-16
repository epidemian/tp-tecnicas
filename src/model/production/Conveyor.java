package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.exception.BusinessLogicException;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class Conveyor extends ProductionLineElement {

	private static final int PRICE = 10;
	
	private final Direction inputConnectionDirection;
	private final Direction outputConnectionDirection;

	public Conveyor() {
		this(Direction.WEST, Direction.EAST);
	}

	public Conveyor(Direction inputConnectionDirection,
			Direction outputConnectionDirection) {
		super(1, 1);

		checkNotNull(inputConnectionDirection, "input connection direction");
		checkNotNull(outputConnectionDirection, "output connection direction");
		if (inputConnectionDirection == outputConnectionDirection)
			throw new BusinessLogicException(
					"input and output connection directions must be different");

		this.inputConnectionDirection = inputConnectionDirection;
		this.outputConnectionDirection = outputConnectionDirection;
	}

	@Override
	public Direction getInputConnectionDirection() {
		return inputConnectionDirection;
	}

	@Override
	public Direction getOutputConnectionDirection() {
		return outputConnectionDirection;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitConveyor(this);
	}

	@Override
	public String toString() {
		return "Conveyor";
	}

	@Override
	public Position getInputConnectionPosition() {
		return this.getPosition().add(
				this.getInputConnectionDirection().getAssociatedPosition());
	}

	@Override
	public Position getOutputConnectionPosition() {
		return this.getPosition().add(
				this.getOutputConnectionDirection().getAssociatedPosition());
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
	
	
}
