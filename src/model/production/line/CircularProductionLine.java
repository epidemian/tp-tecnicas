package model.production.line;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.List;

import model.production.RawMaterials;
import model.production.StorageArea;
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
