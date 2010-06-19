package model.production.elements.machine.states;

import model.production.elements.machine.Machine;

/**
 * Represents the state in which the machine has the lowest defective production
 * rate.
 */
public class HealthyMachineState extends MachineState {

	private static final double SALE_PRICE_COEF = 0.5;
	private static final double DEFECTIVE_PRODUCT_CHANCE = 0.05;
	
    @Override
	public void repair(Machine machine)
			throws CannotRepairHealthyMachineException {
		throw new CannotRepairHealthyMachineException();
	}

    @Override
	public void breakUp(Machine machine) {
		machine.setMachineState(new BrokenMachineState());
		machine.notifyBreakdown();
	}

    @Override
	public void damage(Machine machine) {
		machine.setMachineState(new DamagedMachineState());
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
        public String toString(){
            return "Healthy";
        }
}
