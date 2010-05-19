package model.player;

public class PurchasedFactory extends Factory{
	
	public float Sell(){
		return 0;
	}
	
	public byte GetTypeOperation(){
		return TypeOperation.PURCHASED;
	}
}
