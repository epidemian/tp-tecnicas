package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;
import model.production.ProductionLineElement;

public class Tile {

	private ProductionLineElement lineElement;
	
	public Tile(ProductionLineElement prodLineElement){
		this.lineElement=prodLineElement;
		
	}
	
	public ProductionLineElement getLineElement() {
		return lineElement;
	}
	
	public void setLineElement(ProductionLineElement element) {
		this.lineElement = element;
	}
	
}
