package model.production.elements.machine;

import static model.utils.ArgumentUtils.checkNotNull;
import static model.utils.RandomUtils.randomBoolean;
import model.production.Product;
import model.utils.Config;
import model.warehouse.TileElementVisitor;

public class QualityControlMachine extends Machine {

	public QualityControlMachine(MachineType machineType, Config config) {
		super(machineType,config);
	}

	/*
	 * Uses machine coefficient. The bigger it is, the worse it is. Therefore
	 * the bigger it is, it will discard less defective products.
	 */
	@Override
	public Product treatProduct(Product input) {
		checkNotNull(input, "input product");
		
		boolean discard;
		boolean controlFail = randomBoolean(this.getFailProductProcessChance());
		if (controlFail)
			discard = !input.isDamaged();
		else
			discard = input.isDamaged();
		
		return discard ? null : input;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitQualityControlMachine(this);
	}

}
