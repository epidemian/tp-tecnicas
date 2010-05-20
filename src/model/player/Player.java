package model.player;

import model.warehouse.Ground;
import model.warehouse.Warehouse;

public class Player {
	private Warehouse warehouse;
	private float initialMoney;
	private float moneyEarned;
	private float moneySpent;
	private float valueToWin;
	private int dayForPurchaseRent;
	
	public void Player(float initialMoney, float valueToWin){
		this.initialMoney = initialMoney;
		this.valueToWin = valueToWin;
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
		moneyEarned -= warehouse.getPriceOfRent();	
	}
	
	public boolean purchaseGround(Ground ground){
		if(initialMoney < ground.getPrice()) {
			return false;
		}
		
		warehouse = new Warehouse();
		
		//TODO: Implement
		
		return true;	
	}
	
	public void rentGround(Ground ground, int dayForPurchaseRent){
		this.dayForPurchaseRent = dayForPurchaseRent;
		
		warehouse = new Warehouse();
		
		//TODO: Implement
	}
	
	public void executeDay(){
		//TODO: Implement Method
	}
}