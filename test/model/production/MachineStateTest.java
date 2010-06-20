package model.production;

import static model.production.TestUtils.createMachineType;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import model.game.Budget;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.states.BrokenMachineState;
import model.production.elements.machine.states.DamagedMachineState;
import model.production.elements.machine.states.HealthyMachineState;

import org.junit.Before;
import org.junit.Test;

public class MachineStateTest {

	private Machine machine;

	// At runtime a controller will assign a specific budget from where the
	// line/
	// machine will extract from repair.
	private Budget budget;

	@Before
	public void setUp() {

		machine = new ProductionMachine(createMachineType("TestingMachine"));
		this.budget = new Budget(10000);
	}

	@Test
	public void repairDamagedMachine() {

		machine.setMachineState(new DamagedMachineState());
		machine.repair(budget);

		assertThat(machine.getMachineState(),
				is(instanceOf(HealthyMachineState.class)));
	}

	@Test
	public void repairBrokenMachine() {

		machine.setMachineState(new BrokenMachineState());
		machine.repair(this.budget);

		assertThat(machine.getMachineState(),
				is(instanceOf(HealthyMachineState.class)));
	}

	@Test
	public void breakHealthyMachine() {
		machine.setMachineState(new HealthyMachineState());
		machine.breakUp();
		assertThat(machine.getMachineState(),
				is(instanceOf(BrokenMachineState.class)));

	}
}
