package model.game;

import static model.utils.ArgumentUtils.checkNotNull;
import model.game.time.UpdateScheduler;
import model.warehouse.Ground;

public class Player {
	private Budget budget;
	private float valueToWin;
	private UpdateScheduler scheduler;

	public Player(Budget budget, float valueToWin, UpdateScheduler scheduler) {
		checkNotNull(budget, "budget");
		checkNotNull(scheduler, "update scheduler");

		this.budget = budget;
		this.valueToWin = valueToWin;
		this.scheduler = scheduler;
	}

	private boolean lostGame(int dayBalance) {
		return (budget.getBalance() <= 0) || (dayBalance < 0);
	}

	private boolean winGame() {
		return budget.getBalance() >= valueToWin;
	}

	public boolean purchaseGround(Ground ground) {
		checkNotNull(ground, "ground");
		if(budget.amountCovered(ground.getPrice())){	
			budget.decrement(ground.getPrice());
			return true;
		}
		
		return false;
	}
	
	//TODO: Todo lo que esta comentado va afuera de esta clase segun lo que se hablo con Emiliano
	/*public boolean addResearchLab(ResearchLab researchLab) {
		checkNotNull(researchLab, "researchLab");
		if (this.researchLab == null) {
			this.researchLab = researchLab;
			timeManager.subscribeDailyUpdatable(researchLab);

			return true;
		}

		return false;
	}

	public boolean addWareHouse(Warehouse warehouse) {
		checkNotNull(warehouse, "warehouse");

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

	public void addProductLine(ProductionLine productionLine) {
		checkNotNull(productionLine, "productionLine");
		checkNotNull(warehouse, "warehouse");
		warehouse.getProductionLines().add(productionLine);
		timeManager.subscribeTickUpdatable(productionLine);
	}*/

	public GameState updateTick() {
		int beforeBalance = budget.getBalance();
		scheduler.updateTick();
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