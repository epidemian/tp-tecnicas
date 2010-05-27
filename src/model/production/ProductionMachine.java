package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

public class ProductionMachine extends Machine {

	public ProductionMachine(MachineType machineType, 
			ProductionLineElement next, ProductionLineElement previous) {
		super(machineType, next, previous);
	
		this.productsProcess = 0;
	}
	
	/**
	 * Represents the number of products until one product become defective.
	 * If the product is defective is remains defective. 
	 */
	private int damagedProductFrequency;
	private int productsProcess;
	
	@Override
	public void treatProduct(Product input) {
		checkNotNull(input, "input product");
		this.productsProcess++;
		if (this.damagedProductFrequency == this.productsProcess){
			input.setDefective();
			this.productsProcess = 0;
		}
		
		input.addMachineTypeToHistory(this.getMachineType());
	}
	
	@Override
	public int getPrice() {
		return 0;
	}
}
