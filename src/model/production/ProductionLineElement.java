package model.production;

import static model.production.ProductionLineElementUtils.*;
import java.util.ArrayList;
import java.util.Collection;

import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;

abstract public class ProductionLineElement extends TileElement {

	/**
	 * Output line element of the production line element.
	 */
	private ProductionLineElement nextLineElement;
	/**
	 * Input line element of the production line element.
	 */
	private ProductionLineElement previousLineElement;

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

//	@Override
//	protected final void onAddedToGround(Ground ground) {
//		if (!hasPreviousLineElement()) {
//			for (Position validPrevPos : getValidPreviousLineElementPositions()) {
//				ProductionLineElement lineElement = recognizeProductionLineElement(ground
//						.getTileElementAt(validPrevPos));
//				if (lineElement != null && !lineElement.hasNextLineElement()
//						&& lineElement.canConnectTo(this)) {
//					connectLineElements(lineElement, this);
//					break;
//				}
//			}
//		}
//
//		// Repeat the same for the next element.
//
//	}
//
//	protected abstract boolean canConnectTo(ProductionLineElement lineElement);
//
//	protected abstract Collection<Position> getValidPreviousLineElementPositions();
//
//	protected abstract Collection<Position> getValidNextLineElementPositions();

	private boolean hasPreviousLineElement() {
		return getPreviousLineElement() != null;
	}

	private boolean hasNextLineElement() {
		return getNextLineElement() != null;
	}

	// TODO: Make me private!
	public static void connectLineElements(ProductionLineElement previous,
			ProductionLineElement next) {
		previous.nextLineElement = next;
		next.previousLineElement = previous;
	}
}