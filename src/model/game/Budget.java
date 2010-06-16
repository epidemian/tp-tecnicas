package model.game;

public class Budget {
	private int balance;

	public Budget(int initialBalance) {
		this.balance = initialBalance;
	}

        public Budget() {
            this(0);
        }

        public void setBalance(int balance){
            this.balance = balance;
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
	
	public boolean canPurchase(int amount) {
		return this.balance >= amount;
	}
	
	public boolean amountCovered(int amount) {
		return (this.balance >= amount) && (this.balance >= 0);
	}
	
	public String toString(){
		return "Budget:"+balance;
	}
}
