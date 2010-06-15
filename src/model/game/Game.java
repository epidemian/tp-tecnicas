package model.game;

import static model.utils.ArgumentUtils.*;
import java.util.LinkedList;
import java.util.List;

import model.production.Conveyor;
import model.production.Machine;
import model.production.MachineType;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;

public class Game {

	private Ground ground;
	private List<ProductionLineElement> lineElements;
	private Budget budget;

	public Game(Ground ground) {
		this.ground = ground;
		this.lineElements = new LinkedList<ProductionLineElement>();
		this.budget = new Budget(1000);

		/*
		 * TODO hardcoding just for test.
		 */
		this.lineElements.add(new ProductionMachine(new MachineType("machine1",
				1, 1)));
		this.lineElements.add(new QualityControlMachine(new MachineType(
				"quality1", 1, 1)));
		this.lineElements.add(new Conveyor());

		for (int i = 3; i < 20; i++)
			this.lineElements.add(new ProductionMachine(new MachineType("" + i,
					1, 1)));

	}

	public Ground getGround() {
		return this.ground;
	}

	public List<ProductionLineElement> getProductionLineElements() {
		return this.lineElements;
	}

	public Budget getBudget() {
		return this.budget;
	}

	public boolean canPurchase(int amount) {
		return getBudget().canPurchase(amount);
	}

	public Machine buyAndAddProductionMachine(MachineType machineType,
			Position position) {
		ProductionMachine machine = new ProductionMachine(machineType);
		buyAndAddMachine(machine, position);
		return machine;
	}

	public Machine buyAndAddQualityControlMachine(MachineType machineType,
			Position position) {
		QualityControlMachine machine = new QualityControlMachine(machineType);
		buyAndAddMachine(machine, position);
		return machine;
	}

	private void buyAndAddMachine(Machine machine, Position position) {
		int price = machine.getPrice();
		checkArgCondition(price, canPurchase(price));

		getBudget().decrement(price);
		getGround().putTileElement(machine, position);
	}

	public List<ProductionMachine> getProductionMachines() {
		// TODO Generado para que compile. Terminar!
		return null;
	}

	public List<QualityControlMachine> getQualityControlMachines() {
		// TODO Generado para que compile. Terminar!
		return null;
	}
}
