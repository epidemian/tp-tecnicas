package model.game;

import java.util.ArrayList;
import model.production.StorageArea;
import static model.utils.ArgumentUtils.*;
import java.util.List;

import model.production.Machine;
import model.production.MachineType;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;

public class Game {

	private Ground ground;
        private List<MachineType> qualityControlMachineType;
        private List<MachineType> productionMachinesType;
	private Budget budget;

	public Game(Ground ground) {
		this.ground = ground;
                this.qualityControlMachineType = new ArrayList<MachineType>();
                this.productionMachinesType = new ArrayList<MachineType>();
                this.budget = new Budget(1000);
                
		/*
		 * TODO hardcoding just for test.
		 */
                MachineType prodMachType = new MachineType("productionMachine",3,3,new Position(0, -1), new Position(2, 3),250);
                this.productionMachinesType.add(prodMachType);
                this.productionMachinesType.add(prodMachType);

                MachineType qualMachType = new MachineType("qualityControlMachine",4,3);
                this.qualityControlMachineType.add(qualMachType);
                this.qualityControlMachineType.add(qualMachType);
        }

	public Ground getGround() {
		return this.ground;
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

	public List<MachineType> getProductionMachinesTypes() {
            return this.productionMachinesType;
        }

	public List<MachineType> getQualityControlMachinesTypes() {
            return this.qualityControlMachineType;
	}

    public StorageArea getStorageArea() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
