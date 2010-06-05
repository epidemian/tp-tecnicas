package model.warehouse;

import model.game.Budget;

public class PurchasedWarehouse extends Warehouse{

	public PurchasedWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}

	//TODO: Sacar el 0.8 y ponerlo en configuracion
	public void sellGround() {
		budget.increment((int)(0.8 * ground.getPrice()));
	}

	@Override
	public void updateMonth() {	
		
	}
}
