package model.game;

import static model.utils.ArgumentUtils.*;
import model.game.time.UpdateScheduler;
import model.warehouse.Ground;

public class Player {
	private Budget budget;
	private String name;

	public Player(String name, Budget budget) {

		this.name = name;
		this.budget = budget;
	}

//	private boolean lostGame(int dayBalance) {
//		return (budget.getBalance() <= 0) || (dayBalance < 0);
//	}
//
//	private boolean winGame() {
//		return budget.getBalance() >= valueToWin;
//	}

//	public boolean purchaseGround(Ground ground) {
//		checkNotNull(ground, "ground");
//		if(budget.amountCovered(ground.getPrice())){	
//			budget.decrement(ground.getPrice());
//			return true;
//		}
//		
//		return false;
//	}
//	public GameState updateTick() {
//		int beforeBalance = budget.getBalance();
//		scheduler.updateTick();
//		int affterBalance = budget.getBalance();
//
//		if (winGame()) {
//			return GameState.WIN;
//		} else {
//			if (lostGame(affterBalance - beforeBalance)) {
//				return GameState.LOST;
//			}
//		}
//
//		return GameState.INPROCESS;
//	}

	public Budget getBudget() {
		return this.budget;
	}
}