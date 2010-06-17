package model.production;

import static model.production.TestUtils.*;
import static org.junit.Assert.assertEquals;

import model.game.Budget;
import model.production.elements.ProductionLineElementObserver;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.states.BrokenMachineState;
import model.production.elements.machine.states.CannotRepairHealthyMachineException;
import model.production.elements.machine.states.DamagedMachineState;
import model.production.elements.machine.states.HealthyMachineState;
import model.production.line.ProductionLine;

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
		machine.setProductionLineElementObserver(new ObserverMock());
		this.budget = new Budget(10000);
	}

	@Test(expected = CannotRepairHealthyMachineException.class)
	public void repairAlreadyHealthyMachine()
			throws CannotRepairHealthyMachineException {

		machine.setMachineState(new HealthyMachineState());
		machine.repair(budget);
	}

	@Test
	public void repairDamagedMachine()
			throws CannotRepairHealthyMachineException {

		machine.setMachineState(new DamagedMachineState());
		machine.repair(budget);

		assertEquals(machine.getMachineState(), new HealthyMachineState());
	}

	@Test
	public void repairBrokenMachine()
			throws CannotRepairHealthyMachineException {

		machine.setMachineState(new BrokenMachineState());
		machine.repair(this.budget);

		assertEquals(machine.getMachineState(), new HealthyMachineState());
	}

	@Test
	public void breakHealthyMachine() {
		machine.setMachineState(new HealthyMachineState());
		machine.breakUp();
		assertEquals(machine.getMachineState(), new BrokenMachineState());

	}

	public class ObserverMock implements ProductionLineElementObserver {

		@Override
		public void updateBreakdown(Machine machine) {
		}

		@Override
		public void updateBrokenMachineRepair(Machine machine) {
		}

	}
}
