package model.warehouse;

import model.game.Budget;
import model.game.time.MonthlyUpdatable;

public class RentedWarehouse extends Warehouse {

	public RentedWarehouse(Ground ground, Budget budget) {
		super(ground, budget);
	}

	public void sell() {
		//TODO: Falta sumar el 50% de las m�quinas en buen estado
	}
	
	private void payRentGround(){
		budget.decrement(ground.getPrice());
	}
	
	@Override
	public void updateMonth() {
		payRentGround();
	}
}
