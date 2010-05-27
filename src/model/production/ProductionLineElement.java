package model.production;

abstract public class ProductionLineElement {
	
	/**
	 * Output line element of the production line element.
	 */
	private ProductionLineElement nextLineElement;
	/**
	 * Input line element of the production line element.
	 */
	private ProductionLineElement previousLineElement;
	
	protected Product productContained;
	
	public ProductionLineElement(ProductionLineElement next,
		ProductionLineElement previous){
		this.nextLineElement = next;
		this.previousLineElement = previous;
		this.productContained = null;
	}
	
	/**
	 * Receives a product from the previous line element.
	 * Processes and analyzes if the product becomes defective and logs the
	 * production line element in the product history.
	 * 
	 * @return the product after being processed.
	 */
	public Product process(Product input){
		Product output = this.productContained;
		this.productContained = input;
		return output;
	}
	
	public void setNextLineElement(ProductionLineElement nextLineElement) {
		this.nextLineElement = nextLineElement;
	}
	
	public ProductionLineElement getNextLineElement() {
		return nextLineElement;
	}

	public ProductionLineElement getPreviousLineElement() {
		return previousLineElement;
	}
}
