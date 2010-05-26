package model.warehouse;

import static org.junit.Assert.assertEquals;
import model.game.Budget;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WarehouseTest {
	private static final int INITIAL_BALANCE = 5000;
	private static final int PRICE_GROUND = 1000;
	private static final int PRICE_MACHINES = 0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sellPurcheaseWarehouseAndCheckBalance() {
		Budget budget = new Budget(INITIAL_BALANCE);
		Warehouse purchaseWarehouse = new PurchaseWarehouse(new Ground(PRICE_GROUND, 10, 10), budget); 
		
		purchaseWarehouse.sell();
		//TODO: Ojo que al método sell le falta la parte de la venta de las máquinas en buen estado
		assertEquals(budget.getBalance(), (int)(INITIAL_BALANCE + 0.8 * PRICE_GROUND + 0.5 * PRICE_MACHINES));
	}
	
	@Test
	public void sellRentWarehouseAndCheckBalance() {
		Budget budget = new Budget(INITIAL_BALANCE);
		Warehouse rentWarehouse = new RentedWarehouse(new Ground(PRICE_GROUND, 10, 10), budget); 
		
		rentWarehouse.sell();
		//TODO: Ojo que al método sell le falta la parte de la venta de las máquinas en buen estado
		assertEquals(budget.getBalance(), (int)(INITIAL_BALANCE + 0.5 * PRICE_MACHINES));
	}	
}
