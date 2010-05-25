package model.warehouse;

import java.util.LinkedList;
import java.util.List;

import model.production.ProductionLine;
import model.production.StorageArea;
import model.warehouse.Ground;
import model.production.*;

public class Warehouse {
	
	private Ground ground;
		
	private List<ProductionLineElement> lineElements; 
	
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	private List<ProductionLine> productionLines;
	
	public Warehouse(Ground ground){
		this.ground = ground;
		this.lineElements = new LinkedList<ProductionLineElement>();
		this.productionLines = new LinkedList<ProductionLine>();
	}
	
	public void addProductionLineElement(ProductionLineElement 
			aProductionLineElement){
		this.lineElements.add(aProductionLineElement);
	}
	
	public void createProductionLines(){
		
		while (!this.lineElements.isEmpty()){
			ProductionLineElement firstElement = this.lineElements.get(0);
			
			
		}
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