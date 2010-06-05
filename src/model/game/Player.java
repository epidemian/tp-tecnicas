package model.game;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.Collection;

import model.game.time.TimeManager;
import model.lab.ResearchLab;
import model.production.ProductionLine;
import model.warehouse.Ground;
import model.warehouse.Warehouse;

public class Player {
	private Budget budget;
	private float valueToWin;
	private TimeManager timeManager;
	private Warehouse warehouse;
	private ResearchLab researchLab;

	public Player(Budget budget, float valueToWin, TimeManager timeManager) {
		checkNotNull(budget, "budget");
		checkNotNull(timeManager, "time manager");

		this.budget = budget;
		this.valueToWin = valueToWin;
		this.timeManager = timeManager;
	}

	private boolean lostGame(int dayBalance) {
		return (budget.getBalance() <= 0) || (dayBalance < 0);
	}

	private boolean winGame() {
		return budget.getBalance() >= valueToWin;
	}

	public boolean canPurchaseGround(Ground ground) {
		return ground.getPrice() <= this.budget.getBalance();
	}

	public boolean addResearchLab(ResearchLab researchLab) {
		checkNotNull(researchLab, "researchLab");
		if (this.researchLab == null) {
			this.researchLab = researchLab;
			timeManager.subscribeDailyUpdatable(researchLab);

			return true;
		}

		return false;
	}

	public void deleteResearchLab() {
		this.researchLab = null;
	}

	public boolean addWareHouse(Warehouse warehouse) {
		checkNotNull(warehouse, "warehouse");

		// TODO: Revisar esto. El código dentro del if NUNCA se va a llamar, ya
		// que antes se checkeó que warehouse no es null!
		if (this.warehouse == null) {
			if (budget.getBalance() < warehouse.getPriceGround()) {
				return false;
			}

			Collection<ProductionLine> productionLines = warehouse
					.getProductionLines();

			if (productionLines != null) {
				for (ProductionLine productionLine : productionLines) {
					timeManager.subscribeTickUpdatable(productionLine);
					timeManager.subscribeDailyUpdatable(productionLine);
				}
			}

			timeManager.subscribeMonthlyUpdatable(warehouse);

			budget.decrement(warehouse.getPriceGround());

			this.warehouse = warehouse;

			return true;
		}

		return false;
	}

	public void deleteWareHouse() {
		this.warehouse = null;
	}

	public void addProductLine(ProductionLine productionLine) {
		checkNotNull(productionLine, "productionLine");
		checkNotNull(warehouse, "warehouse");
		warehouse.getProductionLines().add(productionLine);
		timeManager.subscribeTickUpdatable(productionLine);
	}

	public GameState updateTick() {
		int beforeBalance = budget.getBalance();
		timeManager.updateTick();
		int affterBalance = budget.getBalance();

		if (winGame()) {
			return GameState.WIN;
		} else {
			if (lostGame(affterBalance - beforeBalance)) {
				return GameState.LOST;
			}
		}

		return GameState.INPROCESS;
	}
}