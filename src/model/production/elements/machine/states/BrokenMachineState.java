package model.production.elements.machine.states;

import model.game.Budget;
import model.production.elements.machine.Machine;
import model.utils.Config;

/**
 * Represents the state in which the Machine makes the production line not to
 * produce nothing at all. If it's broken or damaged, it won't damage again If
 * it's broken, it will not break again.
 */
public class BrokenMachineState extends MachineState {

	public static float BROKEN_PRICE_REPAIR_COEF = 0.3f;

	public BrokenMachineState(Config config) {
		super(config);
		BROKEN_PRICE_REPAIR_COEF = Float.valueOf(config
				.getValue("BROKEN_PRICE_REPAIR_COEF"));
	}

	@Override
	public void repair(Machine machine, Budget budget) {
		budget.decrement(Math.round(machine.getPurchasePrice()
				* BROKEN_PRICE_REPAIR_COEF));
		machine.setMachineState(new HealthyMachineState(config));
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
