package model.production;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProductionLineTest {

	private ProductionLine productionLine;

	@Before
	public void setUp() {
		this.productionLine = this.createProductionLineProcessingCarton();
	}

	private ProductionLine createProductionLineProcessingCarton() {

		ProductionLineElement prodLineElement1 = new ProductionMachine(
				new MachineType("Licuado"), null, null, 1 ,1);

		ProductionLineElement prodLineElement2 = new ProductionMachine(
				new MachineType("Haz"), null, prodLineElement1, 1 , 1);
		prodLineElement1.setNextLineElement(prodLineElement2);

		ProductionLineElement prodLineElement3 = new ProductionMachine(
				new MachineType("Horno"), null, prodLineElement2, 1 ,1);
		prodLineElement2.setNextLineElement(prodLineElement3);

		return ProductionLine.createValidProductionLine(prodLineElement1,
				new StorageArea(new RawMaterials(),
						new ValidProductionSequences()), new RawMaterials());
	}

	// TODO: ver comentario en Machine#equals()
	@Test
	@Ignore
	public void equalsTest() {

		ProductionLine prodLineEquals = this
				.createProductionLineProcessingCarton();

		assertEquals(prodLineEquals, this.productionLine);
	}

	@Test
	public void dailyProduction() {

		int ticksInADay = 500;

		for (int ticks = 0; ticks < ticksInADay; ticks++) {
			this.productionLine.updateTick();
		}

		int dailyProduction = this.productionLine.getDailyProduction();

		assertEquals(dailyProduction, ticksInADay
				- this.productionLine.productionLineSize());

		this.productionLine.updateDay();

		List<Integer> dailyProductionList = this.productionLine
				.getProductionHistory();

		assertEquals(dailyProductionList.get(dailyProductionList.size() - 1)
				.intValue(), ticksInADay
				- this.productionLine.productionLineSize());

		assertEquals(0, this.productionLine.getDailyProduction());
	}

	/*
	 * public void workingProductionLineWhenMachineIsBroken(){ ProductionLine
	 * line = this.createProductionLineProcessingCarton();
	 * assertTrue(line.isWorking());
	 * 
	 * line.getFirstLineElement(). }
	 */

	/**
	 * TODO: Una vez que se acople al visitor se tratara distinto esto! La idea
	 * de la prueba es solo ver que el repair y la deteccion este funcionando
	 * bien...
	 */
	@Test
	public void LineWithThreeNonBrokenMachines()
			throws CannotRepairHealthyMachineException {
		MachineMock machineMock1 = new MachineMock(new MachineType("Licuado"),
				null, null);

		MachineMock machineMock2 = new MachineMock(new MachineType("Haz"),
				null, machineMock1);
		machineMock1.setNextLineElement(machineMock2);

		MachineMock machineMock3 = new MachineMock(new MachineType("Horno"),
				null, machineMock2);
		machineMock2.setNextLineElement(machineMock3);

		ProductionLine line = ProductionLine.createValidProductionLine(
				machineMock1, new StorageArea(new RawMaterials(),
						new ValidProductionSequences()), new RawMaterials());

		machineMock1.breakMachine();
		machineMock2.breakMachine();

		// Should be false because two machines are broken
		assertFalse(line.isWorking());

		machineMock1.repair();

		// Should be false because one machine is broken
		assertFalse(line.isWorking());

		machineMock2.repair();

		// All machines are repaired
		assertTrue(line.isWorking());
	}

	/**
	 * In order to be able to avoid probability
	 * 
	 */
	// TODO: Esto no es un mock...
	private class MachineMock extends ProductionMachine {

		private MachineMock(MachineType machineType,
				ProductionLineElement next, ProductionLineElement previous) {
			super(machineType, next, previous, 1 , 1);
		}

		public void breakMachine() {
			super.breakMachine();
		}

		public void damageMachine() {
			super.damageMachine();
		}
	}
}
