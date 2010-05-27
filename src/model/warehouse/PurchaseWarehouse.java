package model.warehouse;

import model.game.Budget;

public class PurchaseWarehouse extends Warehouse{

	public PurchaseWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}

	public void sellGround() {
		budget.increment((int)(0.8 * ground.getPrice()));
	}

	@Override
	public void updateMonth() {	
		
	}
}
