package model.game;

import java.util.LinkedList;
import java.util.List;

import model.production.Conveyor;
import model.production.MachineType;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.Ground;

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
}
