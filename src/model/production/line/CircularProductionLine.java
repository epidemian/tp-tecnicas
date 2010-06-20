package model.production.line;

import model.production.elements.ProductionLineElement;

class CircularProductionLine extends ProductionLine {

	protected CircularProductionLine(ProductionLineElement firstLineElement) {
		super(firstLineElement);
	}

	@Override
	public void updateTick() {
		this.breakAllMachines();
	}

	@Override
	public void updateDay() {
	}


}
