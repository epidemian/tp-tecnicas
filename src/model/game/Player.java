package model.game;

import model.warehouse.Ground;

import model.game.Budget;

public class Player {
	private Budget budget; 
	private float valueToWin;
	
	public Player(Budget budget, float valueToWin){
		this.budget = budget;
		this.valueToWin = valueToWin;
	}
	
	private boolean lostGame(){
		return budget.getBalance() <= 0;
	}
	
	private boolean winGame(){
		return budget.getBalance() >= valueToWin;	
	}
	
	public boolean purchaseGround(Ground ground){
		if(budget.getBalance() < ground.getPrice()) {
			return false;
		}
		
		budget.decrement(ground.getPrice());
		
		return true;	
	}
}