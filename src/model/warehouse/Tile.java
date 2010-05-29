package model.warehouse;

import model.production.ProductionLineElement;

public class Tile {

	private ProductionLineElement lineElement;
	
	public Tile(ProductionLineElement prodLineElement){
		this.lineElement=prodLineElement;
		
	}
	
	public Tile(){
		this.lineElement=null;
		
	}
	
	public ProductionLineElement getLineElement() {
		return lineElement;
	}
	
	public void setLineElement(ProductionLineElement element) {
		this.lineElement = element;
	}
	
}
