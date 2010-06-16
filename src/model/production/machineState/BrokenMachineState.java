package model.production.machineState;

import model.production.Machine;

/**
 * Represents the state in which the Machine makes the production line not to
 * produce nothing at all. If it's broken or damaged, it won't damage again If
 * it's broken, it will not break again.
 */
public class BrokenMachineState extends MachineState {

	public static final Float DEFECT_BREAK_CHANCE = 0.02f;

	public void repair(Machine machine) {
		machine.setMachineState(new HealthyMachineState());
		machine.setCurrentCoef(MachineState.HEALTH_DEFECTIVE_COEF);
		machine.notifyBrokenMachineRepair();
	}

	public int getSalePrice(Machine machine) {
		return 0;
	}
}
