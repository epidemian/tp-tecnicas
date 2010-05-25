package model.game;

import model.warehouse.Ground;
import model.warehouse.Warehouse;

public class Player {
	private Warehouse warehouse;
	private float initialMoney = 0;
	private float moneyEarned = 0;
	private float moneySpent = 0;
	private float valueToWin = 0;
	private int dayForPurchaseRent = 0;
	
	public void Player(float initialMoney, float valueToWin){
		this.initialMoney = initialMoney;
		this.valueToWin = valueToWin;
	}
	
	private float getBalance(){
		return (initialMoney + moneyEarned) - moneySpent;	
	}
	
	private boolean lostGame(){
		return (getBalance() <= 0) || (warehouse.getDailyBalance() < 0);
	}
	
	private boolean winGame(){
		return getBalance() >= valueToWin;	
	}
	
	private void payOfRent(){		
		moneySpent += warehouse.getPriceOfRent();	
	}
	
	public boolean purchaseGround(Ground ground){
		if(initialMoney < ground.getPrice()) {
			return false;
		}
		
		warehouse = new Warehouse(ground);
		
		moneySpent += ground.getPrice();
		
		//TODO: How to Set state of purchase?
		
		return true;	
	}
	
	public void rentGround(Ground ground, int dayForPurchaseRent){
		this.dayForPurchaseRent = dayForPurchaseRent;
		
		warehouse = new Warehouse(ground);
		
		//TODO: How to Set state of rent?
	}
	
	public void executeDay(){
		//TODO: Implement Method
	}
}