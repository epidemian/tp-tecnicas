package model.warehouse;

import java.util.LinkedList;
import java.util.List;

import model.production.ProductionLine;
import model.production.ProductionLineElement;
import model.production.StorageArea;
import model.warehouse.Ground;
import model.production.*;

public class Warehouse {
	
	private Ground ground;
		
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	private List<ProductionLine> productionLines;
	
	public Warehouse(Ground ground){
		this.ground = ground;
		this.productionLines = new LinkedList<ProductionLine>();
	}
	
	public void createProductionLines(){
		
		List<ProductionLineElement> touchedElements
			= new LinkedList<ProductionLineElement>();
		
		for (int i = 0; i < this.ground.getRows(); i++)
			for (int j = 0; j < this.ground.getRows(); j++){
				
				ProductionLineElement lineElement = this.ground.getTile(i, j)
					.getLineElement();
				
				if (lineElement != null){
					this.processLineElement(lineElement,touchedElements);
				}
			}
		
	}
	
	private void processLineElement(ProductionLineElement lineElement,
			List<ProductionLineElement> touchedElements) {
		// TODO Coming in a few hours.
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