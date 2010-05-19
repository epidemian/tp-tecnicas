package model.player;

public class Factory {
	//TODO: How to use ProductionLines?
	//protected  ProductionLines;
	protected Ground ground;
	
	abstract float Sell();
	abstract byte GetTypeOperation();
	
	public Factory(Ground ground){
		this.ground = ground;
	}
	
	public void ExecuteTurn(){
		//TODO: Implement Method
	}
	
	public void AddProductionLine(){
		//TODO: Implement Method
	}
	
	public void RemoveProductionLine(){
		//TODO: Implement Method
	}
	
	public void UpdateProductionLine(){
		//TODO: Implement Method
	}
	
	public float GetDailyBalance(){
		//TODO: Implement Method
		return 0;
	}
}
