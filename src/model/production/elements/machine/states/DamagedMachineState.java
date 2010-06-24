package model.production.elements.machine.states;

import model.game.Budget;
import model.production.elements.machine.Machine;
import model.utils.Config;

/**
 * Represents the state of a machine which has a higher rate of defective
 * products than a healthy machine If its damaged, it won't damage again.
 */
public class DamagedMachineState extends MachineState {

	private static float DAMAGED_SALE_PRICE_COEF = 0.5f;
	private static float DAMAGED_DEFECTIVE_PRODUCT_CHANCE = 0.15f;
	private static float DAMAGED_PRICE_REPAIR_COEF = 0.2f;

	public DamagedMachineState(Config config) {
		super(config);
		DAMAGED_SALE_PRICE_COEF = config
				.getFloatValue("DAMAGED_SALE_PRICE_COEF");
		DAMAGED_DEFECTIVE_PRODUCT_CHANCE = config
				.getFloatValue("DAMAGED_DEFECTIVE_PRODUCT_CHANCE");
		DAMAGED_PRICE_REPAIR_COEF = config
				.getFloatValue("DAMAGED_PRICE_REPAIR_COEF");

	}

	@Override
	public void repair(Machine machine, Budget budget) {
		budget.decrement(Math.round(machine.getPurchasePrice()
				* DAMAGED_PRICE_REPAIR_COEF));
		machine.setMachineState(new HealthyMachineState(this.config));
	}

	@Override
	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState(this.config));
	}

	@Override
	public int getSalePrice(Machine machine) {
		return (int) (Math.round(machine.getPurchasePrice()
				* DAMAGED_SALE_PRICE_COEF));
	}

	@Override
	public double getFailProductProcessChance(Machine machine) {
		return DAMAGED_DEFECTIVE_PRODUCT_CHANCE;
	}

	@Override
	public String toString() {
		return "Damaged";
	}
}
