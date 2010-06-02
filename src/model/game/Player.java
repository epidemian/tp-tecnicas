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
	private ResearchLab researchLab;
		
	public Player(Budget budget, float valueToWin, int ticksPerDay, int daysPerMonth)
	{
		checkNotNull(budget, "budget");
			
		this.budget = budget; 
		this.valueToWin = valueToWin;
		this.timeManager= new TimeManager(ticksPerDay, daysPerMonth);
	}
	
	private boolean lostGame(int dayBalance){
		return (budget.getBalance() <= 0) || (dayBalance < 0);
	}
	
	private boolean winGame(){
		return budget.getBalance() >= valueToWin;	
	}
		
	public boolean canPurchaseGround(Ground ground){
		checkNotNull(ground, "ground");
		if(budget.getBalance() < ground.getPrice()) {
			return false;
		}
				
		return true;	
	}
	
	public boolean addResearchLab(ResearchLab researchLab){
		checkNotNull(researchLab, "researchLab");
		if(this.researchLab == null){		
			this.researchLab = researchLab;
			timeManager.subscribeDailyUpdatable(researchLab);
			
			return true;
		}
		
		return false;
	}
	
	public boolean addWareHouse(Warehouse warehouse){
		checkNotNull(warehouse, "warehouse");
		
		if(this.warehouse == null){		
			if(budget.getBalance() < warehouse.getPriceGround()) {
				return false;
			}
									
			Collection<ProductionLine> productionLines = warehouse.getProductionLines();
			
			if(productionLines != null){
				for (ProductionLine productionLine : productionLines) {
					timeManager.subscribeTickUpdatable(productionLine); 
					timeManager.subscribeDailyUpdatable(productionLine);
				}
			}
					
			timeManager.subscribeMonthlyUpdatable(warehouse);
			
			budget.decrement(warehouse.getPriceGround());
			
			this.warehouse = warehouse;
			
			return true;
		}
		
		return false;
	}

	public void addProductLine(ProductionLine productionLine){
		checkNotNull(productionLine, "productionLine");
		checkNotNull(warehouse, "warehouse");
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