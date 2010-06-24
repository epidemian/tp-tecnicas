package model.production.elements;

import model.exception.BusinessLogicException;
import model.production.Direction;
import model.production.RawMaterials;
import model.utils.Config;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class InputProductionLineElement extends ProductionLineElement {

	public static int INPUT_PURCHASE_PRICE;
	private static int INPUT_SALE_PRICE;
	private final Direction outputConnectionDirection;
	private RawMaterials configuration = new RawMaterials();

	public InputProductionLineElement(Direction direction, Config config) {
		super(1, 1);
		this.outputConnectionDirection = direction;

		INPUT_SALE_PRICE = Integer.valueOf(config.getValue("INPUT_SALE_PRICE"));
		INPUT_PURCHASE_PRICE = Integer.valueOf(config
				.getValue("INPUT_PURCHASE_PRICE"));
	}

	public InputProductionLineElement(Config config) {
		this(Direction.EAST, config);
	}

	@Override
	public boolean canHavePreviousLineElement() {
		return false;
	}

	@Override
	public Direction getInputConnectionDirection() {
		throw new BusinessLogicException("Illegal operation");
	}

	@Override
	public Position getInputConnectionRelativePosition() {
		throw new BusinessLogicException("Illegal operation");
	}

	@Override
	public Direction getOutputConnectionDirection() {
		return this.outputConnectionDirection;
	}

	@Override
	public Position getOutputConnectionRelativePosition() {
		return this.getOutputConnectionDirection().getAssociatedPosition();
	}

	@Override
	public int getPurchasePrice() {
		return INPUT_PURCHASE_PRICE;
	}

	@Override
	public int getSalePrice() {
		return INPUT_SALE_PRICE;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitInputProductionLineElement(this);
	}

	public RawMaterials getRawMaterialsConfiguration() {
		return this.configuration;
	}

}
