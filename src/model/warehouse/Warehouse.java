package model.warehouse;

import java.util.LinkedList;
import java.util.List;

import model.production.ProductionLine;
import model.production.ProductionLineElement;
import model.production.StorageArea;
import model.warehouse.Ground;

public class Warehouse {
	
	private Ground ground;
		
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	private List<ProductionLine> productionLines;
	
	public Warehouse(Ground ground){
		this.ground = ground;
	}
	
	public void createProductionLines(){
		
		productionLines = new LinkedList<ProductionLine>();
		
		List<ProductionLineElement> touchedElements
			= new LinkedList<ProductionLineElement>();
		
		for (int i = 0; i < this.ground.getRows(); i++)
			for (int j = 0; j < this.ground.getRows(); j++){
				
				ProductionLineElement lineElement = this.ground.getTile(i, j)
					.getLineElement();
				
				if (lineElement != null && !touchedElements
						.contains(lineElement)){
					this.productionLines.add(this
							.processLineElement(lineElement,touchedElements));
				}
			}
	}
	
	private ProductionLine processLineElement(ProductionLineElement lineElement,
			List<ProductionLineElement> touchedElements) {
		
		touchedElements.add(lineElement);
		
		boolean circularLine = false;
		ProductionLineElement previous = lineElement.getPreviousLineElement();
		
		/*
		 * Try to find the first element in the line. 
		 */
		while (previous != null && !circularLine){
		
			if (previous == lineElement)
				circularLine = true;
			else
				touchedElements.add(previous);
			
			previous = previous.getPreviousLineElement();
		}
		
		/*
		 * Add to the touchedElements list the production elements between 
		 * lineElement and the last one in the line. 
		 */
		if (!circularLine){
			
			ProductionLineElement next = lineElement.getNextLineElement();
			
			while (next != null){
				touchedElements.add(next);
				next = lineElement.getNextLineElement();
			}
		}
				
		return !circularLine ? ProductionLine
								.createCircularProductionLine(previous)
							 : ProductionLine
							 	.createValidProductionLine(previous);
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
	
	public float getDailyBalance(){
		//TODO: Implement Method
		return 0;
	}
}