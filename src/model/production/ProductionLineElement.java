package model.production;

import model.game.Budget;
import model.warehouse.Position;
import model.warehouse.TileElement;

abstract public class ProductionLineElement extends TileElement {

	/**
	 * Output line element of the production line element.
	 */
	private ProductionLineElement nextLineElement = null;
	/**
	 * Input line element of the production line element.
	 */
	private ProductionLineElement previousLineElement = null;

	protected Product productContained;

	private ProductionLineElementObserver prodLineElementObserver;

	public ProductionLineElement(int width, int height) {
		super(width, height);
	}

	/**
	 * Receives a product from the previous line element. Processes and analyzes
	 * if the product becomes defective and logs the production line element in
	 * the product history.
	 * 
	 * @return the product after being processed.
	 */
	public Product process(Product input) {
		Product output = this.productContained;
		this.productContained = input;
		return output;
	}

	public ProductionLineElement getNextLineElement() {
		return nextLineElement;
	}

	public ProductionLineElement getPreviousLineElement() {
		return previousLineElement;
	}

	public void setProductionLineElementObserver(
			ProductionLineElementObserver obs) {
		this.prodLineElementObserver = obs;
	}

	public ProductionLineElementObserver getProductionLineElementObserver() {
		return this.prodLineElementObserver;
	}

	public boolean hasPreviousLineElement() {
		return getPreviousLineElement() != null;
	}

	public boolean hasNextLineElement() {
		return getNextLineElement() != null;
	}

	public static void connectLineElements(ProductionLineElement previous,
			ProductionLineElement next) {
		previous.nextLineElement = next;
		next.previousLineElement = previous;
	}

	public abstract Position getInputConnectionPosition();

	public abstract Position getOutputConnectionPosition();

	public abstract Direction getInputConnectionDirection();

	public abstract Direction getOutputConnectionDirection();
	
	public void repair(Budget budget)
		throws CannotRepairHealthyMachineException {}
	
	protected void breakUp(){}
	
}