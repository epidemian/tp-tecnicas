package model.production;

public class ProductionMachine extends Machine {

	public ProductionMachine(MachineType machineType, 
			ProductionLineElement next, ProductionLineElement previous) {
		super(machineType, next, previous);
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
	public Product process(Product input) {
		super.process(input);
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getPrice() {
		return 0;
	}
}
