package model.game.player;

import static org.junit.Assert.*;
import model.game.Budget;
import model.game.GameState;
import model.game.Player;
import model.game.time.TimeManager;
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

	@Before
	public void setUp() throws Exception {
		this.budget = new Budget(INITIAL_BALANCE);
		TimeManager timeManager = new TimeManager(TICKS_PER_DAY, DAYS_PER_WEEK,
				DAYS_PER_MONTH);
		this.player = new Player(budget, WIN_VALUE, timeManager);
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
		this.budget = new Budget(INITIAL_BALANCE);

		TimeManager timeManager = new TimeManager(TICKS_PER_DAY, DAYS_PER_WEEK,
				DAYS_PER_MONTH);
		this.player = new Player(budget, WIN_VALUE, timeManager);
		
		ResearchLab researchLab = new ResearchLab(new TechnologyTree(), 10,
				budget);

		Ground ground = new Ground(GROUND_PRICE, 10, 10);
		Warehouse warehouse = new PurchasedWarehouse(ground, budget);

		timeManager.subscribeDailyUpdatable(researchLab);
		timeManager.subscribeMonthlyUpdatable(warehouse);
		
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
