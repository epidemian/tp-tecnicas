package model.game;

public class Budget {

	private int balance;

	public Budget(int initialBalance) {
		this.balance = initialBalance;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void increment(int amount) {
		this.balance += amount;
	}
	
	public void decrement(int amount) {
		this.balance -= amount;
	}
	
}
