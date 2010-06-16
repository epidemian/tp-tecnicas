package model.production.machineState;

import model.production.Machine;

public abstract class MachineState {

	// probability of making the product defective when being on each state.
	public static final double DAMAGED_DEFECTIVE_COEF = 0.15;
	public static final double HEALTH_DEFECTIVE_COEF = 0.05;

	public abstract void repair(Machine machine)
			throws CannotRepairHealthyMachineException;

	public void breakUp(Machine machine) {
	}

	public void damage(Machine machine) {
	}

	public abstract int getSalePrice(Machine machine);

	public boolean equals(Object anotherObj) {
		return this.getClass().equals(((MachineState) anotherObj).getClass());
	}

}
