package model.production.elements.machine.states;

import model.game.Budget;
import model.production.elements.machine.Machine;
import model.utils.Config;

public abstract class MachineState {
	
	protected Config config;

	public MachineState(Config config) {
		super();
		this.config = config;
	}

	public abstract void repair(Machine machine, Budget budget);

	public void breakUp(Machine machine) {
	}

	public void damage(Machine machine) {
	}

	public abstract int getSalePrice(Machine machine);


	public abstract double getFailProductProcessChance(Machine machine);

	public boolean isBroken() {
		return false;
	}

}
