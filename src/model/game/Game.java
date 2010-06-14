package model.game;

import static model.utils.ArgumentUtils.*;
import java.util.LinkedList;
import java.util.List;

import model.production.Conveyor;
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

    public Game(Ground ground){
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

    public Ground getGround(){
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

	public void buyAndAddProductionMachine(MachineType machineType,
			Position position) {
		int price = machineType.getPrice();
		checkArgCondition(price, canPurchase(price));
		
		getGround().putTileElement(new ProductionMachine(machineType), position);
		getBudget().decrement(price);
	}
}
