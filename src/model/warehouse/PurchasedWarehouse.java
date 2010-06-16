package model.warehouse;

import model.game.Budget;
import model.production.ValidProductionSequences;

public class PurchasedWarehouse extends Warehouse{

	//TODO remove!
	public PurchasedWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}

	public PurchasedWarehouse(Ground ground, Budget budget, PriceMap map,
			ValidProductionSequences sequences) {
		super(ground, budget, map, sequences);
		// TODO Auto-generated constructor stub
	}
	
	

	//TODO: Sacar el 0.8 y ponerlo en configuracion
	public void sellGround() {
		budget.increment((int)(0.8 * ground.getPrice()));
	}

	@Override
	public void updateMonth() {	
		
	}
}
