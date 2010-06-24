package model.production.elements.machine;

import static model.utils.ArgumentUtils.checkNotNull;
import static model.utils.RandomUtils.randomBoolean;
import model.production.Product;
import model.utils.Config;
import model.warehouse.TileElementVisitor;

public class ProductionMachine extends Machine {

	/*
	 * public ProductionMachine(MachineType machineType, int width, int height)
	 * { super(machineType, width, height); this.productsProcess = 0; }
	 */
	public ProductionMachine(MachineType machineType,Config config) {
		super(machineType, config);
		// this.productsProcess = 0;
	}

	/*
	 * DEPRECATED Represents the number of products until one product become
	 * defective. If the product is defective is remains defective.
	 */
	// private int damagedProductFrequency;
	// private int productsProcess;
	/*
	 * @Override public void treatProduct(Product input) { checkNotNull(input,
	 * "input product"); this.productsProcess++; if
	 * (this.damagedProductFrequency == this.productsProcess) {
	 * input.setDefective(); this.productsProcess = 0; }
	 * 
	 * input.addMachineTypeToHistory(this.getMachineType()); }
	 */

	@Override
	/*
	 * Uses machine coefficient. The bigger it is, the worse it is. Therefore
	 * the bigger it is, more defective products will be made!
	 */
	public Product treatProduct(Product input) {
		checkNotNull(input, "input product");
		if (randomBoolean(this.getFailProductProcessChance())) {
			input.setDefective();
		}
		input.addMachineTypeToHistory(this.getMachineType());
		return input;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitProductionMachine(this);
	}
}
