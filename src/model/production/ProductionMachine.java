package model.production;

public class ProductionMachine extends Machine {

	
	public ProductionMachine(MachineType machineType) {
		super(machineType);
	}

	/**
	 * number between 0-1 that represents the probability of damaging 
	 * one product
	 */
	private float damagedProductFrequency;
	
	public boolean isProductionMachine(){
		return true;
	}

	@Override
	public int getPrice() {
		return 0;
	}
}
