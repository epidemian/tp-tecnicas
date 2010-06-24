package model.production.elements;

import static model.utils.ArgumentUtils.checkNotNull;
import model.exception.BusinessLogicException;
import model.production.Direction;
import model.utils.Config;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class Conveyor extends ProductionLineElement {

	public static int CONVEYOR_PURCHASE_PRICE;
	public static int CONVEYOR_SALE_PRICE;
	public static final int WIDTH = 1;
	public static final int HEIGHT = 1;

	private final Direction inputConnectionDirection;
	private final Direction outputConnectionDirection;

	public Conveyor(Config config) {
		this(Direction.WEST, Direction.EAST, config);
	}

	public Conveyor(Direction inputConnectionDirection,
			Direction outputConnectionDirection, Config config) {
		super(1, 1);

		checkNotNull(inputConnectionDirection, "input connection direction");
		checkNotNull(outputConnectionDirection, "output connection direction");
		if (inputConnectionDirection == outputConnectionDirection)
			throw new BusinessLogicException(
					"input and output connection directions must be different");

		this.inputConnectionDirection = inputConnectionDirection;
		this.outputConnectionDirection = outputConnectionDirection;

		CONVEYOR_SALE_PRICE = config.getIntegerValue("CONVEYOR_SALE_PRICE");
		CONVEYOR_PURCHASE_PRICE = config
				.getIntegerValue("CONVEYOR_PURCHASE_PRICE");
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
	public Position getInputConnectionRelativePosition() {
		return this.getInputConnectionDirection().getAssociatedPosition();
	}

	@Override
	public Position getOutputConnectionRelativePosition() {
		return this.getOutputConnectionDirection().getAssociatedPosition();
	}

	@Override
	public int getPurchasePrice() {
		return CONVEYOR_PURCHASE_PRICE;
	}

	@Override
	public int getSalePrice() {
		return CONVEYOR_SALE_PRICE;
	}

}
