package model.production;

public class QualityControlMachine extends Machine{

	public QualityControlMachine(MachineType machineType, 
			ProductionLineElement next, ProductionLineElement previous) {
		super(machineType, next, previous);
	}
	
	/**
	 * number between 0-1 that represents the probability of discarding one product
	 * from the line of production. 
	 */
	private float discardDamagedFrequency;
	
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
