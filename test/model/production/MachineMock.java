package model.production;

/**
 * Internal class of tests created to avoid problems caused by the probability 
 * when trying repair/break problems.
 */
public class MachineMock extends ProductionMachine {
	
	public MachineMock(MachineType machineType) {
		super(machineType);
	}

	public void breakUp() {
		super.breakUp();
	}

	public void damage() {
		super.damage();
	}
}