package model.production.machineState;

import model.production.Machine;

/**
 * Represents the state of a machine which has a higher rate of defective
 * products than a healthy machine If its damaged, it won't damage again.
 */
public class DamagedMachineState extends MachineState {

	public static final double SALE_PRICE_COEF = 0.5;
	public static final Float DEFECT_DAMAGE_CHANCE = 0.15f;

	public void repair(Machine machine) {
		machine.setMachineState(new HealthyMachineState());
		machine.setCurrentCoef(MachineState.HEALTH_DEFECTIVE_COEF);
	}

	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState());
		machine.notifyBreakdown();
	}

	public int getSalePrice(Machine machine) {
		return (int) (Math.round(machine.getPurchasePrice() * SALE_PRICE_COEF));
	}
}
