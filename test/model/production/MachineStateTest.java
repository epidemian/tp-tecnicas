package model.production;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MachineStateTest {
	private MachineMock machine;
	private ProductionLine line;

	@Before
	public void setUp() {

		machine = new MachineMock(new MachineType("TestingMachine",1,1));
		line = ProductionLine.createValidProductionLine(machine,
				new StorageArea(new RawMaterials(),
						new ValidProductionSequences()), new RawMaterials());
		machine.setProductionLineElementObserver(line);
	}

	@Test(expected = CannotRepairHealthyMachineException.class)
	public void repairAlreadyHealthyMachine()
			throws CannotRepairHealthyMachineException {

		machine.setMachineState(new HealthyMachineState());
		machine.repair();
	}

	@Test
	public void repairDamagedMachine()
			throws CannotRepairHealthyMachineException {

		machine.setMachineState(new DamagedMachineState());
		machine.repair();

		assertEquals(machine.getMachineState(), new HealthyMachineState());
	}

	@Test
	public void repairBrokenMachine()
			throws CannotRepairHealthyMachineException {

		machine.setMachineState(new BrokenMachineState());
		machine.repair();

		assertEquals(machine.getMachineState(), new HealthyMachineState());
	}

	@Test
	public void breakHealthyMachine() {
		machine.setMachineState(new HealthyMachineState());
		machine.breakUp();
		assertEquals(machine.getMachineState(), new BrokenMachineState());

	}

	/**
	 * In order to be able to avoid probability
	 * 
	 */
	private class MachineMock extends ProductionMachine {

		private MachineMock(MachineType machineType) {
			super(machineType);
		}

		// TODO: Para qué se redefine esto?
		public void breakUp() {
			super.breakUp();
		}

		public void damage() {
			super.damage();
		}
	}

}
