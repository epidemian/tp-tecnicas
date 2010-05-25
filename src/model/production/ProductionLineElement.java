package model.production;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductionLineElement extends Container{

	/**
	 * Output of the production line element.
	 */
	private ProductionLineElement nextLineElement;
	/**
	 * Input of the production line element.
	 */
	private ProductionLineElement previousLineElement;
		
	public void process(){
		throw new NotImplementedException();
	}
	
	public boolean isProductionMachine(){
		return false;
	}

	public void setNextLineElement(ProductionLineElement nextLineElement) {
		this.nextLineElement = nextLineElement;
	}
	
	public void setPreviousLineElement(ProductionLineElement 
			previousLineElement) {
		this.previousLineElement = previousLineElement;
	}

	public ProductionLineElement getNextLineElement() {
		return nextLineElement;
	}

	public ProductionLineElement getPreviousLineElement() {
		return previousLineElement;
	}
}
