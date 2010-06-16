package model.production;

import static model.production.TestUtils.*;
import static model.production.elements.ProductionLineElement.connectLineElements;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.game.Budget;
import model.production.elements.Conveyor;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.MachineType.Builder;
import model.production.elements.machine.states.CannotRepairHealthyMachineException;
import model.production.line.ProductionLine;
import model.warehouse.Position;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProductionLineTest {

	private ProductionLine productionLine;

	// At runtime a controller will assign a specific budget from where the
	// line/
	// machine will extract from repair.
	private Budget budget;

	@Before
	public void setUp() {
		this.productionLine = this
				.createProductionLineProcessingCartonWithOnlyMachines();
		this.budget = new Budget(10000);
	}

	private ProductionLine createProductionLineProcessingCartonWithOnlyMachines() {

		ProductionLineElement prodLineElement1 = new ProductionMachine(
				createMachineType("Licuado", 1, 1));
		ProductionLineElement prodLineElement2 = new ProductionMachine(
				createMachineType("Haz", 1, 1));
		ProductionLineElement prodLineElement3 = new ProductionMachine(
				createMachineType("Horno", 1, 1));

		connectLineElements(prodLineElement1, prodLineElement2);
		connectLineElements(prodLineElement2, prodLineElement3);

		return ProductionLine.createValidProductionLine(prodLineElement1,
				new StorageArea(new RawMaterials(),
						new ValidProductionSequences()), new RawMaterials());
	}

	private ProductionLine createProductionLineProcessingCartonWithConveyor() {

		ProductionLineElement prodLineElement1 = new ProductionMachine(
				createMachineType("Licuado", 1, 1));
		ProductionLineElement prodLineElement2 = new Conveyor();

		ProductionLineElement prodLineElement3 = new ProductionMachine(
				createMachineType("Horno", 1, 1));

		connectLineElements(prodLineElement1, prodLineElement2);
		connectLineElements(prodLineElement2, prodLineElement3);

		return ProductionLine.createValidProductionLine(prodLineElement1,
				new StorageArea(new RawMaterials(),
						new ValidProductionSequences()), new RawMaterials());
	}

	@Test
	public void dailyProduction() {

		int ticksInADay = 500;

		for (int ticks = 0; ticks < ticksInADay; ticks++) {
			this.productionLine.updateTick();
		}

		int dailyProduction = this.productionLine.getDailyProduction();

		int expectedProduction = ticksInADay
				- this.productionLine.productionLineSize();
		assertEquals(expectedProduction, dailyProduction);

		this.productionLine.updateDay();

		List<Integer> dailyProductionList = this.productionLine
				.getProductionHistory();

		assertEquals(dailyProductionList.get(dailyProductionList.size() - 1)
				.intValue(), expectedProduction);

		assertEquals(0, this.productionLine.getDailyProduction());
	}

	private List<MachineMock> createListConnectedMachineMocks() {
		return createListConnectedMachineMocks(0); 
	}
	
	private List<MachineMock> createListConnectedMachineMocks(int price) {
		List<MachineMock> list = new ArrayList<MachineMock>();

		Builder builder = new MachineType.Builder("Licuado", 1, 1).price(price);
		
		MachineMock machineMock1 = new MachineMock(builder.build());
		MachineMock machineMock2 = new MachineMock(builder.name("Haz").build());
		MachineMock machineMock3 = new MachineMock(builder.name("Horno").build());

		connectLineElements(machineMock1, machineMock2);
		connectLineElements(machineMock2, machineMock3);

		list.add(machineMock1);
		list.add(machineMock2);
		list.add(machineMock3);

		return list;
	}

	@Test
	public void LineWithThreeNonBrokenMachinesRepairedFromMachines()
			throws CannotRepairHealthyMachineException {

		List<MachineMock> machines = createListConnectedMachineMocks();

		ProductionLine line = ProductionLine.createValidProductionLine(machines
				.get(0), new StorageArea(new RawMaterials(),
				new ValidProductionSequences()), new RawMaterials());

		machines.get(0).breakUp();
		machines.get(1).breakUp();

		// Should be false because two machines are broken
		assertFalse(line.isWorking());

		machines.get(0).repair(this.budget);

		// Should be false because one machine is broken
		assertFalse(line.isWorking());

		machines.get(1).repair(this.budget);

		// All machines are now repaired!
		assertTrue(line.isWorking());

	}

	@Test
	public void LineWithThreeNonBrokenMachinesRepairedFromLine()
			throws CannotRepairHealthyMachineException {

		List<MachineMock> machines = createListConnectedMachineMocks();

		ProductionLine line = ProductionLine.createValidProductionLine(machines
				.get(0), new StorageArea(new RawMaterials(),
				new ValidProductionSequences()), new RawMaterials());

		machines.get(0).breakUp();
		machines.get(1).breakUp();

		line.repairAllMachines(budget);

		// All machines are now repaired!
		assertTrue(line.isWorking());

	}

	@Test
	public void debitFromBudgetWhenRepairingLine()
			throws CannotRepairHealthyMachineException {
		int initialBudget = this.budget.getBalance();

		List<MachineMock> machines = createListConnectedMachineMocks();

		ProductionLine line = ProductionLine.createValidProductionLine(machines
				.get(0), new StorageArea(new RawMaterials(),
				new ValidProductionSequences()), new RawMaterials());

		// One machine will be broken and the line will be repaired.
		machines.get(0).breakUp();
		assertFalse(line.isWorking());

		line.repairAllMachines(this.budget);

		assertEquals(this.budget.getBalance(), initialBudget
				- Math.round(machines.get(0).getPurchasePrice()
						* Machine.PRICE_REPAIR_COEF));
	}

	@Test
	public void sellMachinesWhenNotBroken() {
		int initialBudget = this.budget.getBalance();

		int price = 100;
		int nMachines = 3;
		List<MachineMock> machines = createListConnectedMachineMocks(price);

		ProductionLine line = ProductionLine.createValidProductionLine(machines
				.get(0), new StorageArea(new RawMaterials(),
				new ValidProductionSequences()), new RawMaterials());

		line.sell(budget);

		int expected = initialBudget + (price / 2) * nMachines;
		assertEquals(expected, budget.getBalance());
	}

	@Test
	public void sellMachinesWhenOneBrokenAndOneDamaged() {
		int initialBudget = this.budget.getBalance();

		int price = 100;
		List<MachineMock> machines = createListConnectedMachineMocks(price);

		ProductionLine line = ProductionLine.createValidProductionLine(machines
				.get(0), new StorageArea(new RawMaterials(),
				new ValidProductionSequences()), new RawMaterials());

		machines.get(0).damage();
		// machines.get(0).breakUp();
		machines.get(1).breakUp();

		line.sell(budget);

		assertEquals(initialBudget + (price / 2) * 2 , budget.getBalance());
	}

	@Test
	public void breakAllMachinesInATwoMachineLine() {
		ProductionLine line = this
				.createProductionLineProcessingCartonWithConveyor();

		line.breakAllMachines();

		assertEquals(line.getBrokenMachines().size(), 2);

	}

}
