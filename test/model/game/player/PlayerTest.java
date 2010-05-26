package model.game.player;


import static org.junit.Assert.assertEquals;
import model.game.Budget;
import model.game.Player;
import model.warehouse.Ground;
import model.warehouse.PurchaseWarehouse;
import model.warehouse.Warehouse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {
	private static final int INITIAL_BALANCE = 5000;
	private static final int VALUE_TO_WIN = 10000;
	private static final int PRICE_GROUND = 1000;	

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
	public void checkPurchaseGroundAndCheckBalance() {
		Budget budget = new Budget(INITIAL_BALANCE);
		Player player = new Player(budget, VALUE_TO_WIN);
		
		assertEquals(player.purchaseGround(new Ground(PRICE_GROUND + INITIAL_BALANCE + 1000, 10, 10)), false);
		assertEquals(player.purchaseGround(new Ground(PRICE_GROUND, 10, 10)), true);
		assertEquals(budget.getBalance(), INITIAL_BALANCE - PRICE_GROUND);
	}	
}
