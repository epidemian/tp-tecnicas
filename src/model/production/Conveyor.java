package model.production;

import model.warehouse.TileElementVisitor;

public class Conveyor extends ProductionLineElement{

	public Conveyor(ProductionLineElement next, ProductionLineElement previous) {
		super(next, previous);
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitConveyor(this);
	}
}
