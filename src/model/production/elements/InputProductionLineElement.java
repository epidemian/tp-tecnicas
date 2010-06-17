package model.production.elements;

import model.production.Direction;
import model.production.RawMaterials;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class InputProductionLineElement extends ProductionLineElement {

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
	public Direction getInputConnectionDirection() {
		return null;
	}

	@Override
	public Position getInputConnectionPosition() {
		return null;
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
		return 0;
	}

	@Override
	public int getSalePrice() {
		return 0;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitInputProductionLineElement(this);
	}

	public RawMaterials getRawMaterialsConfiguration() {
		return this.configuration ;
	}

}
