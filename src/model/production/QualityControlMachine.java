package model.production;

public class QualityControlMachine extends Machine{

	public QualityControlMachine(MachineType machineType) {
		super(machineType);
	}

	/**
	 * number between 0-1 that represents the probability of discarding one product
	 * from the line of production. 
	 */
	private float discardDamagedFrequency; 
}
