package model.production.elements;

import model.exception.BusinessLogicException;
import model.production.Direction;
import model.production.RawMaterials;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class InputProductionLineElement extends ProductionLineElement {

	public static final int PURCHASE_PRICE = 0;
	private static final int SALE_PRICE = 0;
	private final Direction outputConnectionDirection;
	private RawMaterials configuration = new RawMaterials();

	public InputProductionLineElement(Direction direction) {
		super(1, 1);
		this.outputConnectionDirection = direction;
	}

	public InputProductionLineElement() {
		this(Direction.EAST);
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
	public Position getInputConnectionPosition() {
		throw new BusinessLogicException("Illegal operation");
	}

	@Override
	public Direction getOutputConnectionDirection() {
		return this.outputConnectionDirection;
	}

	@Override
	public Position getOutputConnectionPosition() {
		return this.getPosition().add(
				this.getOutputConnectionDirection().getAssociatedPosition());
	}

	@Override
	public int getPurchasePrice() {
		return PURCHASE_PRICE;
	}

	@Override
	public int getSalePrice() {
		return SALE_PRICE;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitInputProductionLineElement(this);
	}

	public RawMaterials getRawMaterialsConfiguration() {
		return this.configuration ;
	}

}
