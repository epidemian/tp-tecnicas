package model.production;

import static model.production.ProductionLineElementUtils.recognizeProductionLineElement;

import java.util.Collection;

import model.warehouse.Ground;
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

	@Override
	protected final void onAddedToGround(Ground ground) {
		if (!hasPreviousLineElement()) {
			for (Position validPrevPos : getValidPreviousLineElementPositions()) {
				ProductionLineElement lineElement = recognizeProductionLineElement(ground
						.getTileElementAt(validPrevPos));
				if (canBePreviousLineElement(lineElement)) {
					connectLineElements(lineElement, this);
					break;
				}
			}
		}

		if (!hasNextLineElement()) {
			for (Position validNextPos : getValidNextLineElementPositions()) {
				ProductionLineElement lineElement = recognizeProductionLineElement(ground
						.getTileElementAt(validNextPos));
				if (canBeNextLineElement(lineElement)) {
					connectLineElements(this, lineElement);
					break;
				}
			}
		}
	}

	protected abstract boolean canConnectToByType(
			ProductionLineElement lineElement);

	protected abstract Collection<Position> getValidPreviousLineElementPositions();

	protected abstract Collection<Position> getValidNextLineElementPositions();

	protected void setNextLineElement(ProductionLineElement nextLineElement) {
		this.nextLineElement = nextLineElement;
	}

	protected void setPreviousLineElement(
			ProductionLineElement previousLineElement) {
		this.previousLineElement = previousLineElement;
	}

	private boolean canBePreviousLineElement(ProductionLineElement lineElement) {
		return lineElement != null
				&& !lineElement.hasNextLineElement()
				&& containsAnyPosition(lineElement
						.getValidNextLineElementPositions())
				&& lineElement.canConnectToByType(this);
	}

	private boolean canBeNextLineElement(ProductionLineElement lineElement) {
		return lineElement != null
				&& !lineElement.hasPreviousLineElement()
				&& containsAnyPosition(lineElement
						.getValidPreviousLineElementPositions())
				&& lineElement.canConnectToByType(this);
	}

	private boolean containsAnyPosition(Collection<Position> positions) {
		for (Position pos : positions) {
			Position diff = pos.subtract(getPosition());
			if (0 <= diff.getRow() && diff.getRow() < getHeight()
					&& 0 <= diff.getCol() && diff.getCol() < getWidth())
				return true;
		}
		return false;
	}

	public boolean hasPreviousLineElement() {
		return getPreviousLineElement() != null;
	}

	public boolean hasNextLineElement() {
		return getNextLineElement() != null;
	}

	// TODO: Make me private!
	private static void connectLineElements(ProductionLineElement previous,
			ProductionLineElement next) {
		previous.setNextLineElement(next);
		next.setPreviousLineElement(previous);
	}
}