package model.game;

public class Budget {
	private float balance;

	public Budget(float initialBalance) {
		this.balance = initialBalance;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public void increment(float amount) {
		this.balance += amount;
	}
	
	public void decrement(float amount) {
		this.balance -= amount;
	}
	
}
