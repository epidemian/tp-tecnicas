package model.production.elements.machine;

import static model.utils.ArgumentUtils.checkNotNull;
import static model.utils.RandomUtils.randomBoolean;
import model.production.Product;
import model.warehouse.TileElementVisitor;

public class QualityControlMachine extends Machine {

	public QualityControlMachine(MachineType machineType) {
		super(machineType);
	}

	/*
	 * DEPRECATED Represents the number of defective products until one product
	 * become non-defective.
	 */
	// private int discardDamagedFrequency;
	// private int defectiveProducts;
	/*
	 * @Override public void treatProduct(Product input) { checkNotNull(input,
	 * "input product"); if (input.isDamaged()) { this.defectiveProducts++; if
	 * (this.discardDamagedFrequency == this.defectiveProducts) input = null; }
	 * }
	 */

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
