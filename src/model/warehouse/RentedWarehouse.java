package model.warehouse;

import model.game.Budget;
import model.production.ValidProductionSequences;

public class RentedWarehouse extends Warehouse {

	//TODO remove!
	public RentedWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}
	
	public RentedWarehouse(Ground ground, Budget budget, PriceMap map,
			ValidProductionSequences sequences) {
		super(ground, budget, map, sequences);
		// TODO Auto-generated constructor stub
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
