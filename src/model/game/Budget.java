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
	
	public boolean amountCovered(int amount) {
		return (this.balance >= amount) && (this.balance >= 0);
	}
	
	public String toString(){
		return "Budget:"+balance;
	}
}
