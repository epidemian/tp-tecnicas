package model.production.elements.machine.states;

import model.production.elements.machine.Machine;

public abstract class MachineState {


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

	public abstract double getFailProductProcessChance(Machine machine);

}
