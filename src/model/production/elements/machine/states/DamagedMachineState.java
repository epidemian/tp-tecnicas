package model.production.elements.machine.states;

import model.game.Budget;
import model.production.elements.machine.Machine;

/**
 * Represents the state of a machine which has a higher rate of defective
 * products than a healthy machine If its damaged, it won't damage again.
 */
public class DamagedMachineState extends MachineState {

	private static final float SALE_PRICE_COEF = 0.5f;
	private static final float DEFECTIVE_PRODUCT_CHANCE = 0.15f;
	private static final float PRICE_REPAIR_COEF = 0.2f;

	@Override
	public void repair(Machine machine, Budget budget) {
		budget.decrement(Math.round(machine.getPurchasePrice()
				* PRICE_REPAIR_COEF));
		machine.setMachineState(new HealthyMachineState());
	}

	@Override
	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState());
	}

	@Override
	public int getSalePrice(Machine machine) {
		return (int) (Math.round(machine.getPurchasePrice() * SALE_PRICE_COEF));
	}

	@Override
	public double getFailProductProcessChance(Machine machine) {
		return DEFECTIVE_PRODUCT_CHANCE;
	}

	@Override
	public String toString() {
		return "Damaged";
	}
}
