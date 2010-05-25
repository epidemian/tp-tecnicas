package model.warehouse;

import model.game.Budget;

public class RentedWarehouse extends Warehouse{

	public RentedWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}

	public void sell() {
		//TODO: Falta sumar el 50% de las máquinas en buen estado
	}
}
