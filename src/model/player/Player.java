package model.player;

import model.warehouse.Warehouse;

public class Player {
	private Warehouse warehouse;
	private float initialMoney;
	private float moneyEarned;
	private float moneySpent;
	private float valueToWin;
	private int dayForPurchaseRent;
	
	public void Player(float initialMoney, float valueToWin, int dayForPurchaseRent){
		this.initialMoney = initialMoney;
		this.valueToWin = valueToWin;
		this.dayForPurchaseRent = dayForPurchaseRent;
	}
	
	private float getBalance(){
		return moneyEarned - moneySpent;	
	}
	
	private boolean lostGame(){
		return (getBalance() <= 0) || (warehouse.getDailyBalance() < 0);
	}
	
	private boolean winGame(){
		return getBalance() >= valueToWin;	
	}
	
	private void payOfRent(){
		//TODO: Implement Method	
	}
	
	public boolean purchaseGround(){
		//TODO: Implement Method
		return false;	
	}
	
	public void executeDay(){
		//TODO: Implement Method
	}
}