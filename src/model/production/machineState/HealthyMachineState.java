package model.production.machineState;

import model.production.Machine;

/**
 * Represents the state in which the machine has the lowest defective production
 * rate.
 */
public class HealthyMachineState extends MachineState {

	public static final double SALE_PRICE_COEF = 0.5;

	public void repair(Machine machine)
			throws CannotRepairHealthyMachineException {
		throw new CannotRepairHealthyMachineException();
	}

	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState());
		machine.notifyBreakdown();
	}

	public void damage(Machine machine) {
		machine.setMachineState(new DamagedMachineState());
		machine.setCurrentCoef(MachineState.DAMAGED_DEFECTIVE_COEF);
	}

	@Override
	public int getSalePrice(Machine machine) {
		return (int) (Math.round(machine.getPurchasePrice() * SALE_PRICE_COEF));
	}
}
