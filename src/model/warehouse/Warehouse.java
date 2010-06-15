package model.warehouse;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.Collection;

import model.game.Budget;
import model.game.time.MonthlyUpdatable;
import model.production.Machine;
import model.production.ProductionLineElement;
import model.production.StorageArea;
import model.production.line.ProductionLine;

public abstract class Warehouse implements MonthlyUpdatable{
	protected Budget budget; 
	protected Ground ground;
		
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	private Collection<ProductionLine> productionLines;
	
	public Collection<ProductionLine> getProductionLines() {
		return productionLines;
	}

	public Warehouse(Ground ground, Budget budget){
		checkNotNull(ground, "ground");
		checkNotNull(budget, "budget");
		this.ground = ground;
		this.budget = budget;
	}
	
	public void createProductionLines(){
		
		ProductionLinesCreator creator = new ProductionLinesCreator(
				this.storageArea);
		productionLines = creator.createFromGround(this.ground);
	}

	private void sellMachines(){			
		if (this.productionLines != null) {
			for (ProductionLine productionLine : this.productionLines) {
				for (ProductionLineElement element : productionLine) {
					element.sell(budget);
				}
			}
		}
	}	
	
	public int getPriceGround(){
		checkNotNull(ground, "ground");
		return ground.getPrice();
	}
	
	
	protected abstract void sellGround();
	
	public void sell() {
		sellGround(); 
        sellMachines(); 	
	}
}