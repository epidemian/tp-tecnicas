package model.game;

import java.util.LinkedList;
import java.util.List;
import model.production.MachineType;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
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
        for (int i = 0; i < 20; i++)
            this.lineElements.add(new ProductionMachine(new MachineType(new String("" + i)),1,1));
        
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
