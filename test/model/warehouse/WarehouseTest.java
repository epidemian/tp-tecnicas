package model.warehouse;

import static org.junit.Assert.assertEquals;
import model.game.Budget;
import model.production.ValidProductionSequences;

import org.junit.Before;
import org.junit.Test;

public class WarehouseTest {

	private static final int INITIAL_BALANCE = 5000;
	private static final int PRICE_GROUND = 1000;
	// TODO Machine prices.
	private static final int PRICE_MACHINES = 0;
	
	private Warehouse purchasedWarehouse;
	private Warehouse rentedWarehouse;
	private Budget budget;
	
	@Before
	public void setUp() throws Exception {
	
		Ground ground = new Ground(PRICE_GROUND, 10, 10);
		PriceMap map = new PriceMap();
		ValidProductionSequences sequences = new ValidProductionSequences();
		
		this.budget = new Budget(INITIAL_BALANCE);
		this.purchasedWarehouse = Warehouse.createPurchasedWarehouse(ground, budget, map, sequences,null);
		this.rentedWarehouse = Warehouse.createRentedWarehouse(ground, budget, map, sequences,null);
	}

	@Test
	public void sellPurcheaseWarehouseAndCheckBalance() {
		this.purchasedWarehouse.sell();
		assertEquals((int)(INITIAL_BALANCE + 0.8 * this.purchasedWarehouse.getSalePrice() + 0.5 * PRICE_MACHINES), budget.getBalance());
	}
	
	@Test
	public void sellRentWarehouseAndCheckBalance() {
		this.rentedWarehouse.sell();
		assertEquals((int)(INITIAL_BALANCE + 0.5 * PRICE_MACHINES), budget.getBalance());
	}	
	
	@Test
	public void updateMonthAndCheckBalance() {
		this.rentedWarehouse.updateMonth();
		assertEquals((int)(INITIAL_BALANCE - this.rentedWarehouse.getRentPrice()),budget.getBalance());
	}
}
