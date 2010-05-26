package model.warehouse;

import model.game.Budget;

public class PurchaseWarehouse extends Warehouse{

	public PurchaseWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}

	public void sell() {
		//TODO: Falta sumar el 50% de las máquinas en buen estado
		budget.increment((int)(0.8 * ground.getPrice()));
	}
}
