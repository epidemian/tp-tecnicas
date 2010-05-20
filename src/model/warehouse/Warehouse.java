package model.warehouse;

import java.util.ArrayList;
import model.production.ProductionLine;
import model.production.StorageArea;
import model.warehouse.Ground;

public class Warehouse {
	private Ground ground;
		
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	private ArrayList<ProductionLine> productionLines;
	
	public Warehouse(Ground ground){
		this.ground = ground;
	}
	
	public void setPrice(Ground ground) {
		this.ground = ground;
	}
		
	public float sell(){
		float valueOfSell = 0;
		
		//TODO: Implement Price of Machines not broken
		
		if(true){
			valueOfSell += 0.8 * ground.getPrice();
		}
		
		return valueOfSell;
	};
	
	public float getPriceOfRent(){
		return ground.getPrice();
	}
		
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