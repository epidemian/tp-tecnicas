package model.player;

public class RentedFactory  extends Factory{
	
	public float Sell(){
		return 0;
	}
	
	public byte GetTypeOperation(){
		return TypeOperation.RENTED;
	}
}
