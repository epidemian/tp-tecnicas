package model.production.elements.machine.states;

import model.exception.BusinessLogicException;
import model.production.elements.machine.Machine;

/**
 * Represents the state in which the Machine makes the production line not to
 * produce nothing at all. If it's broken or damaged, it won't damage again If
 * it's broken, it will not break again.
 */
public class BrokenMachineState extends MachineState {

	@Override
	public void repair(Machine machine) {
		machine.setMachineState(new HealthyMachineState());
		machine.notifyBrokenMachineRepair();
	}

	@Override
	public int getSalePrice(Machine machine) {
		return 0;
	}

	@Override
	public double getFailProductProcessChance(Machine machine) {
		return Double.NaN;
	}

	@Override
	public String toString() {
		return "Broken";
	}
	
	@Override
	public boolean isBroken() {
		return true;
	}
	
}
