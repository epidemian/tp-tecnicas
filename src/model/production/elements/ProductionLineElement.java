package model.production.elements;

import model.exception.BusinessLogicException;
import model.game.Budget;
import model.production.Direction;
import model.production.Product;
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
		if (!canHaveNextLineElement())
			throw new BusinessLogicException("Can't have next element");
		return nextLineElement;
	}

	public ProductionLineElement getPreviousLineElement() {
		if (!canHavePreviousLineElement())
			throw new BusinessLogicException("Can't have previous element");
		return previousLineElement;
	}

	public boolean canHavePreviousLineElement() {
		return true;
	}

	public boolean canHaveNextLineElement() {
		return true;
	}

	public boolean hasPreviousLineElement() {
		if (!canHavePreviousLineElement())
			return false;
		return this.previousLineElement != null;
	}

	public boolean hasNextLineElement() {
		if (!canHaveNextLineElement())
			return false;
		return this.nextLineElement != null;
	}

	public static void connectLineElements(ProductionLineElement previous,
			ProductionLineElement next, ConnectionRules rules) {
		if (!previous.canHaveNextLineElement()
				|| !next.canHavePreviousLineElement()
				|| !rules.canConnect(previous, next))
			throw new BusinessLogicException("Invalid connection [previous="
					+ previous + ", next=" + next + "]");
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

	public final Position getInputConnectionPosition() {
		return getPosition().add(getInputConnectionRelativePosition());
	}

	public final Position getOutputConnectionPosition() {
		return getPosition().add(getOutputConnectionRelativePosition());
	}

	public abstract Position getOutputConnectionRelativePosition();

	public abstract Position getInputConnectionRelativePosition();

	public abstract Direction getInputConnectionDirection();

	public abstract Direction getOutputConnectionDirection();

	public void repair(Budget budget) {
	}

	public void breakUp() {
	}

	public final void sell(Budget budget) {
		budget.increment(this.getSalePrice());
	}

	public abstract int getPurchasePrice();

	public abstract int getSalePrice();

	public boolean isBroken() {
		return false;
	}

}