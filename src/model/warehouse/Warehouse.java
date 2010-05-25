package model.warehouse;

import java.util.LinkedList;
import java.util.List;

import model.production.ProductionLine;
import model.production.ProductionLineElement;
import model.production.StorageArea;
import model.warehouse.Ground;

import model.game.Budget;
import model.game.time.DailyUpdatable;;

public abstract class Warehouse implements DailyUpdatable {
	protected Budget budget; 
	protected Ground ground;
		
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	private List<ProductionLine> productionLines;
	
	public Warehouse(Ground ground, Budget budget){
		this.ground = ground;
		this.budget = budget;
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

	public abstract void sell();	
}