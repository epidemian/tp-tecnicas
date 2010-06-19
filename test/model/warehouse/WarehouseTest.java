package model.warehouse;

import static org.junit.Assert.assertEquals;
import model.game.Budget;
import model.production.ValidProductionSequences;

import org.junit.Before;
import org.junit.Test;

public class WarehouseTest {

	private static final int INITIAL_BALANCE = 5000;
	private static final int GROUND_PURCHASE_PRICE = 1000;

	private Budget budget;
	private Ground ground;
	private MarketPrices marketPrices;
	private ValidProductionSequences sequences;

	private Warehouse warehouse;

	@Before
	public void setUp() throws Exception {

		this.ground = new Ground(GROUND_PURCHASE_PRICE, 10, 10);
		this.marketPrices = new MarketPrices();
		this.sequences = new ValidProductionSequences();
		this.budget = new Budget(INITIAL_BALANCE);
	}

	private void purchaseWarehouse() {
		this.warehouse = Warehouse.purchaseWarehouse(ground, budget,
				marketPrices, sequences);
	}

	private void rentWarehouse() {
		this.warehouse = Warehouse.rentWarehouse(ground, budget, marketPrices,
				sequences);
	}

	@Test
	public void sellPurcheaseWarehouseAndCheckBalance() {
		purchaseWarehouse();
		this.warehouse.sell();
		int expectedBalance = INITIAL_BALANCE - GROUND_PURCHASE_PRICE
				+ this.warehouse.getSalePrice();

		assertEquals(expectedBalance, budget.getBalance());
	}

	@Test
	public void sellRentWarehouseAndCheckBalance() {
		rentWarehouse();
		this.warehouse.sell();

		assertEquals(INITIAL_BALANCE, budget.getBalance());
	}

	@Test
	public void updateMonthAndCheckBalance() {
		rentWarehouse();
		this.warehouse.updateMonth();		
		int expectedBalance = INITIAL_BALANCE
				- this.warehouse.getRentPrice();

		assertEquals(expectedBalance, budget.getBalance());
	}
}
