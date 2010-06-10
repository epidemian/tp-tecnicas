package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.warehouse.TileElementVisitor;

public class ProductionMachine extends Machine {

	/*
	public ProductionMachine(MachineType machineType, int width, int height) {
		super(machineType, width, height);
		this.productsProcess = 0;
	}
	*/
	public ProductionMachine(MachineType machineType){
		super(machineType);
		this.productsProcess = 0;
	}

	/**
	 * Represents the number of products until one product become defective. If
	 * the product is defective is remains defective.
	 */
	private int damagedProductFrequency;
	private int productsProcess;

	@Override
	public void treatProduct(Product input) {
		checkNotNull(input, "input product");
		this.productsProcess++;
		if (this.damagedProductFrequency == this.productsProcess) {
			input.setDefective();
			this.productsProcess = 0;
		}

		input.addMachineTypeToHistory(this.getMachineType());
	}

	@Override
	public int getPrice() {
		return 0;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitProductionMachine(this);
	}
}
