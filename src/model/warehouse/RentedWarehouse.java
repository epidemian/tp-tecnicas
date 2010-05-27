package model.warehouse;

import model.game.Budget;

public class RentedWarehouse extends Warehouse {

	public RentedWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}
	
	private void payRentGround(){
		budget.decrement(ground.getPrice());
	}
	
	@Override
	public void updateMonth() {
		payRentGround();
	}

	@Override
	protected void sellGround() {}
}
