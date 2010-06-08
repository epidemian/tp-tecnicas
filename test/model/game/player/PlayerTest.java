package model.game.player;

import static org.junit.Assert.*;
import model.game.Budget;
import model.game.GameState;
import model.game.Player;
import model.game.time.UpdateScheduler;
import model.lab.ResearchLab;
import model.lab.TechnologyTree;
import model.warehouse.Ground;
import model.warehouse.PurchasedWarehouse;
import model.warehouse.Warehouse;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private static final int INITIAL_BALANCE = 5000;
	private static final int WIN_VALUE = 4000;
	private static final int GROUND_PRICE = 1000;
	private static final int TICKS_PER_DAY = 20;
	private static final int DAYS_PER_WEEK = 5;
	private static final int DAYS_PER_MONTH = 10;
	private Budget budget;
	private Player player;
	private UpdateScheduler scheduler;

	@Before
	public void setUp() throws Exception {
		this.budget = new Budget(INITIAL_BALANCE);
		this.scheduler = new UpdateScheduler(TICKS_PER_DAY, DAYS_PER_WEEK,
				DAYS_PER_MONTH);
		this.player = new Player(budget, WIN_VALUE, this.scheduler);
	}

	@Test
	public void canPurchaseGroundWithPriceLessThanBalance() {
		Ground ground = createGroundWithDefaultPrice();
		assertTrue(player.purchaseGround(ground));
		assertEquals(INITIAL_BALANCE - GROUND_PRICE, budget.getBalance());
	}

	@Test
	public void canPurchaseGroundWithPriceEqualToBalance() {
		Ground ground = createGroundByPrice(INITIAL_BALANCE);
		assertTrue(player.purchaseGround(ground));
		assertEquals(INITIAL_BALANCE - INITIAL_BALANCE, budget.getBalance());
	}

	@Test
	public void canPurchaseGroundWithPriceBiggerThanBalance() {
		Ground ground = createGroundByPrice(INITIAL_BALANCE + 1);
		assertFalse(player.purchaseGround(ground));
		assertEquals(INITIAL_BALANCE, budget.getBalance());
	}

	@Test
	public void purchaseGroundAndCheckBalance() {
		Ground ground = createGroundWithDefaultPrice();
		player.purchaseGround(ground);

		assertEquals(INITIAL_BALANCE - ground.getPrice(), budget.getBalance());
	}

	@Test
	public void checkWinGame() {
		ResearchLab researchLab = new ResearchLab(new TechnologyTree(), 10,
				budget);

		Ground ground = createGroundByPrice(INITIAL_BALANCE);
		Warehouse warehouse = new PurchasedWarehouse(ground, budget);

		this.scheduler.subscribeDailyUpdatable(researchLab);
		this.scheduler.subscribeMonthlyUpdatable(warehouse);
		
		GameState gameState = player.updateTick();
		
		assertEquals(GameState.WIN, gameState);
	}

	private Ground createGroundByPrice(int price) {
		return new Ground(price, 10, 10);
	}

	private Ground createGroundWithDefaultPrice() {
		return createGroundByPrice(GROUND_PRICE);
	}
}
