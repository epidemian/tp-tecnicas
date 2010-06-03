package model.production;

import model.warehouse.TileElementVisitor;

public class Conveyor extends ProductionLineElement{

//	public Conveyor(ProductionLineElement next, ProductionLineElement previous) {
//		super(next, previous, 1, 1);
//	}

	public Conveyor() {
		super(1, 1);
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitConveyor(this);
	}
	
	@Override
	public String toString(){
		return "Conveyor";
	}
}
