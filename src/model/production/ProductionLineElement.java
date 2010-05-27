package model.production;

import java.util.LinkedList;
import java.util.Queue;

abstract public class ProductionLineElement {

	/**
	 * Output line element of the production line element.
	 */
	private ProductionLineElement nextLineElement;
	/**
	 * Input line element of the production line element.
	 */
	private ProductionLineElement previousLineElement;
	
	private Queue<Product> productsContained;
	
	public ProductionLineElement(ProductionLineElement next,
		ProductionLineElement previous){
		this.nextLineElement = next;
		this.previousLineElement = previous;
		this.productsContained = new LinkedList<Product>();
	}
	
	/**
	 * Receives a product from the previous line element.
	 * Processes and analyzes if the product becomes defective and logs the
	 * production line element in the product history.
	 * 
	 * @return the product after being processed.
	 */
	abstract public Product process(Product input);
	
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
