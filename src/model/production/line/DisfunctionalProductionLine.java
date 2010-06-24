package model.production.line;

import model.production.elements.ProductionLineElement;

class DisfunctionalProductionLine extends ProductionLine {

	protected DisfunctionalProductionLine(ProductionLineElement firstLineElement) {
		super(firstLineElement);
	}

	@Override
	public void updateTick() {
	}

	@Override
	public void updateDay() {
	}

	@Override
	public boolean isWorking() {
		return false;
	}
}
