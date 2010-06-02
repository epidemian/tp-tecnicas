package model.game;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.Collection;
import model.warehouse.Ground;
import model.warehouse.Warehouse;

import model.game.Budget;
import model.game.time.TimeManager;
import model.lab.ResearchLab;
import model.production.ProductionLine;

public class Player {
	private Budget budget; 
	private float valueToWin;
	private TimeManager timeManager;
	private Warehouse warehouse;
		
	public Player(Budget budget, Warehouse warehouse, ResearchLab researchLab, 
			float valueToWin, int ticksPerDay, int daysPerMonth)
	{
		checkNotNull(budget, "budget");
		checkNotNull(warehouse, "warehouse");
		checkNotNull(researchLab, "researchLab");
			
		this.budget = budget;
		this.timeManager= new TimeManager(ticksPerDay, daysPerMonth); 
		this.valueToWin = valueToWin;
		this.warehouse = warehouse;
		
		Collection<ProductionLine> productionLines = warehouse.getProductionLines();
		
		if(productionLines != null){
			for (ProductionLine productionLine : productionLines) {
				timeManager.subscribeTickUpdatable(productionLine); 
				timeManager.subscribeDailyUpdatable(productionLine);
			}
		}
				
		timeManager.subscribeDailyUpdatable(researchLab);
		timeManager.subscribeMonthlyUpdatable(warehouse);
	}
	
	private boolean lostGame(int dayBalance){
		return (budget.getBalance() <= 0) || (dayBalance < 0);
	}
	
	private boolean winGame(){
		return budget.getBalance() >= valueToWin;	
	}
	
	public boolean purchaseGround(Ground ground){
		checkNotNull(ground, "ground");
		if(budget.getBalance() < ground.getPrice()) {
			return false;
		}
		
		budget.decrement(ground.getPrice());
		
		return true;	
	}
	
	void addProductLine(ProductionLine productionLine){
		checkNotNull(productionLine, "productionLine");
		warehouse.getProductionLines().add(productionLine);
		timeManager.subscribeTickUpdatable(productionLine);
	}
	
	public GameState updateTick() {
		int beforeBalance = budget.getBalance();
	    timeManager.updateTick();
	    int affterBalance = budget.getBalance();
	    
	    if(winGame()){
	    	return GameState.WIN;
	    }else{
	    	if(lostGame(affterBalance - beforeBalance)){
	    		return GameState.LOST;
	    	}
	    }
	    
		return GameState.INPROCESS;
	}
}