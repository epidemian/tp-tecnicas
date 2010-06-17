package model.production.elements;

import model.game.Budget;
import model.production.Direction;
import model.production.Product;
import model.production.elements.machine.states.CannotRepairHealthyMachineException;
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
	
	public static void disconnectLineElement(ProductionLineElement element) {
		if (element.hasPreviousLineElement()) {
			element.previousLineElement.nextLineElement = null;
			element.previousLineElement = null;
		}
		if (element.hasNextLineElement()) {
			element.nextLineElement.previousLineElement = null;
			element.nextLineElement = null;
		}
	}

	public abstract Position getInputConnectionPosition();

	public abstract Position getOutputConnectionPosition();

	public abstract Direction getInputConnectionDirection();

	public abstract Direction getOutputConnectionDirection();
	
	public void repair(Budget budget)
		throws CannotRepairHealthyMachineException {}
	
	public void breakUp(){}

	public final void sell(Budget budget) {
		budget.increment(this.getSalePrice());
	}

	public abstract int getPurchasePrice();

	public abstract int getSalePrice();
	
}