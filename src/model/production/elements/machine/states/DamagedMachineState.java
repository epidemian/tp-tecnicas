package model.production.elements.machine.states;

import model.production.elements.machine.Machine;

/**
 * Represents the state of a machine which has a higher rate of defective
 * products than a healthy machine If its damaged, it won't damage again.
 */
public class DamagedMachineState extends MachineState {

	private static final double SALE_PRICE_COEF = 0.5;
	private static final double DEFECTIVE_PRODUCT_CHANCE = 0.15;
	
	public void repair(Machine machine) {
		machine.setMachineState(new HealthyMachineState());
	}

	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState());
		machine.notifyBreakdown();
	}

	public int getSalePrice(Machine machine) {
		return (int) (Math.round(machine.getPurchasePrice() * SALE_PRICE_COEF));
	}

	@Override
	public double getMakeDefectiveProductChance(Machine machine) {
		return DEFECTIVE_PRODUCT_CHANCE;
	}
}
