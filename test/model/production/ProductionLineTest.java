package model.production;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.game.Budget;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.MachineType.Builder;
import model.production.elements.machine.states.BrokenMachineState;
import model.production.line.ProductionLine;
import model.utils.ConfigMock;

import org.junit.Before;
import org.junit.Test;

public class ProductionLineTest {

	private static final int MACHINE_PRICE = 100;

	private static final int INITIAL_BUDGET = 10000;

	private ProductionLine productionLine;

	List<Machine> machines;

	// At runtime a controller will assign a specific budget from where the
	// line/
	// machine will extract from repair.
	private Budget budget;

	@Before
	public void setUp() {
		this.machines = new ArrayList<Machine>();
		this.budget = new Budget(INITIAL_BUDGET);
	}

	private void setUpProductionLineProcessingCartonWithOnlyMachines() {

		Builder builder = new MachineType.Builder("Licuado", 1, 1)
				.price(MACHINE_PRICE);

		InputProductionLineElement inputElement = new InputProductionLineElement(
				new ConfigMock());

		Machine machine1 = new MachineThatNeverBreaks(builder.build());
		Machine machine2 = new MachineThatNeverBreaks(builder.name("Haz")
				.build());
		Machine machine3 = new MachineThatNeverBreaks(builder.name("Horno")
				.build());
		OutputProductionLineElement outputElement = new OutputProductionLineElement();

		connectLineElements(inputElement, machine1);
		connectLineElements(machine1, machine2);
		connectLineElements(machine2, machine3);
		connectLineElements(machine3, outputElement);

		this.machines.add(machine1);
		this.machines.add(machine2);
		this.machines.add(machine3);

		ValidProductionSequences validSequencesMock = new ValidProductionSequences() {

			@Override
			public ProductType identifyProductType(
					ProductionSequence productionSequence) {
				return new ProductType("Product");
			}

		};

		StorageArea storageArea = new StorageArea(validSequencesMock) {

			@Override
			public Product createProduct(RawMaterials inputRawMaterials) {
				return new Product(new RawMaterials());
			}

		};

		this.productionLine = ProductionLine.createFunctionalProductionLine(
				storageArea, inputElement, outputElement);
	}

	private static void connectLineElements(ProductionLineElement previous,
			ProductionLineElement next) {
		ProductionLineElement.connectLineElements(previous, next,
				new PermissiveConnectionRules());
	}

	@Test
	public void dailyProduction() {

		setUpProductionLineProcessingCartonWithOnlyMachines();

		int ticksInADay = 500;

		for (int ticks = 0; ticks < ticksInADay; ticks++) {
			this.productionLine.updateTick();
		}

		int dailyProduction = this.productionLine.getDailyProduction();

		int expectedProduction = ticksInADay
				- this.productionLine.productionLineSize();

		assertEquals(expectedProduction, dailyProduction);

		this.productionLine.updateDay();

		List<Integer> productionHistory = this.productionLine
				.getProductionHistory();

		assertEquals(expectedProduction, productionHistory.get(
				productionHistory.size() - 1).intValue());

		assertEquals(0, this.productionLine.getDailyProduction());
	}

	// private List<Machine> createListConnectedMachines() {
	// return createListConnectedMachines(0);
	// }

	@Test
	public void lineWithThreeNonBrokenMachinesRepairedFromMachines() {

		setUpProductionLineProcessingCartonWithOnlyMachines();

		machines.get(0).breakUp();
		machines.get(1).breakUp();

		// Should be false because two machines are broken
		assertFalse(this.productionLine.isWorking());

		machines.get(0).repair(this.budget);

		// Should be false because one machine is broken
		assertFalse(this.productionLine.isWorking());

		machines.get(1).repair(this.budget);

		// All machines are now repaired!
		assertTrue(this.productionLine.isWorking());
	}

	@Test
	public void lineWithThreeNonBrokenMachinesRepairedFromLine() {

		setUpProductionLineProcessingCartonWithOnlyMachines();

		machines.get(0).breakUp();
		machines.get(1).breakUp();

		this.productionLine.repairAllMachines(budget);

		// All machines are now repaired!
		assertTrue(this.productionLine.isWorking());

	}

	@Test
	public void debitFromBudgetWhenRepairingLine() {

		setUpProductionLineProcessingCartonWithOnlyMachines();

		// One machine will be broken and the line will be repaired.
		machines.get(0).breakUp();
		assertFalse(this.productionLine.isWorking());

		this.productionLine.repairAllMachines(this.budget);

		int expectedBalance = INITIAL_BUDGET
				- Math.round(machines.get(0).getPurchasePrice()
						* BrokenMachineState.BROKEN_PRICE_REPAIR_COEF);

		assertEquals(expectedBalance, this.budget.getBalance());
	}

	@Test
	public void sellMachinesWhenNotBroken() {
		int price = MACHINE_PRICE;
		int nMachines = 3;
		setUpProductionLineProcessingCartonWithOnlyMachines();

		this.productionLine.sell(budget);

		int expectedBalance = INITIAL_BUDGET + (price / 2) * nMachines;
		assertEquals(expectedBalance, budget.getBalance());
	}

	@Test
	public void sellMachinesWithOneBroken() {
		int price = 100;
		setUpProductionLineProcessingCartonWithOnlyMachines();

		// machines.get(0).damage();
		// machines.get(0).breakUp();
		machines.get(1).breakUp();

		this.productionLine.sell(budget);

		int expectedBalance = INITIAL_BUDGET + (price / 2) * 2;
		assertEquals(expectedBalance, budget.getBalance());
	}

}

class MachineThatNeverBreaks extends ProductionMachine {

	public MachineThatNeverBreaks(MachineType machineType) {
		super(machineType, new ConfigMock());
	}

	@Override
	protected boolean willBreakUp() {
		return false;
	}
}
