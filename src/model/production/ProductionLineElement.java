package model.production;

import java.util.LinkedList;
import java.util.Queue;

abstract public class ProductionLineElement {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nextLineElement == null) ? 0 : nextLineElement.hashCode());
		result = prime
				* result
				+ ((previousLineElement == null) ? 0 : previousLineElement
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionLineElement other = (ProductionLineElement) obj;
		if (nextLineElement == null) {
			if (other.nextLineElement != null)
				return false;
		} else if (!nextLineElement.equals(other.nextLineElement))
			return false;
		if (previousLineElement == null) {
			if (other.previousLineElement != null)
				return false;
		} else if (!previousLineElement.equals(other.previousLineElement))
			return false;
		return true;
	}

	/**
	 * Output line element of the production line element.
	 */
	private ProductionLineElement nextLineElement;
	/**
	 * Input line element of the production line element.
	 */
	private ProductionLineElement previousLineElement;
	
	protected Queue<Product> productsContained;
	
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
	public Product process(Product input){
		Product output = this.productsContained.poll();
		if (input != null)
			this.productsContained.add(input);
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
