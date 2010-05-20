package model.warehouse;

public abstract class Warehouse {
	//TODO: How to use ProductionLines?
	//protected  ProductionLines;
	protected Ground ground;
	
	public abstract float sell();
		
	public void executeTurn(){
		//TODO: Implement Method
	}
	
	public void addProductionLine(){
		//TODO: Implement Method
	}
	
	public void removeProductionLine(){
		//TODO: Implement Method
	}
	
	public void updateProductionLine(){
		//TODO: Implement Method
	}
	
	public float getDailyBalance(){
		//TODO: Implement Method
		return 0;
	}
}