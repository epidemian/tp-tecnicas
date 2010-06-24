package model.production.elements.machine.states;

import model.game.Budget;
import model.production.elements.machine.Machine;
import model.utils.Config;

/**
 * Represents the state in which the machine has the lowest defective production
 * rate.
 */
public class HealthyMachineState extends MachineState {

	private static float HEALTHY_SALE_PRICE_COEF = 0.5f;
	private static float HEALTHY_DEFECTIVE_PRODUCT_CHANCE = 0.05f;

	public HealthyMachineState(Config config) {
		super(config);
		HEALTHY_DEFECTIVE_PRODUCT_CHANCE=Float.valueOf(config.getValue("HEALTHY_DEFECTIVE_PRODUCT_CHANCE"));
		HEALTHY_SALE_PRICE_COEF=Float.valueOf(config.getValue("HEALTHY_SALE_PRICE_COEF"));
	}

	@Override
	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState(config));
	}

	@Override
	public void damage(Machine machine) {
		machine.setMachineState(new DamagedMachineState(config));
	}

	@Override
	public int getSalePrice(Machine machine) {
		return (int) (Math.round(machine.getPurchasePrice() * HEALTHY_SALE_PRICE_COEF));
	}

	@Override
	public double getFailProductProcessChance(Machine machine) {
		return HEALTHY_DEFECTIVE_PRODUCT_CHANCE;
	}

	@Override
	public String toString() {
		return "Healthy";
	}

	@Override
	public void repair(Machine machine, Budget budget) {
	}
}
