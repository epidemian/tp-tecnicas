package model.player;

public class Player {
	private Factory factory;
	private float initialMoney;
	private float moneyEarned;
	private float moneySpent;
	private float valueToWin;
	private int dayForPurchaseRent;
	
	public void Player(float initialMoney, float valueToWin, int dayForPurchaseRent){
		this.initialMoney = initialMoney;
		this.valueToWin = valueToWin;
		this.dayForPurchaseRent = dayForPurchaseRent;
		factory = new Factory();
	}
	
	private float GetBalance(){
		return moneyEarned - moneySpent;	
	}
	
	private boolean LostGame(){
		//TODO: 
		float factoryBalanceForDay = 0;
		return (GetBalance() <= 0) || (factoryBalanceForDay < 0);
	}
	
	private boolean WinGame(){
		return GetBalance() >= valueToWin;	
	}
	
	private void PayOfRent(){
		//TODO: Implement Method	
	}
	
	public boolean PurchaseGround(){
		//TODO: Implement Method
		return false;	
	}
	
	public void ExecuteDay(){
		//TODO: Implement Method
	}
}
