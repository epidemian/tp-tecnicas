package model.warehouse;

import static model.utils.ArgumentUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.utils.Config;

public class Ground {
	
	private static double GROUND_RENT_PRICE_COEF;
	private static double GROUND_SALE_PRICE_COEF;
	
	private int price;
	private TileElement tileElements[][];
	private EmptyTileElement emptyElement;

	public Ground(int price, int rows, int cols, Config config) {
		
		GROUND_RENT_PRICE_COEF = Double.valueOf(config.getValue("GROUND_RENT_PRICE_COEF"));
		GROUND_SALE_PRICE_COEF = Double.valueOf(config.getValue("GROUND_SALE_PRICE_COEF"));
		
		this.price = price;
		checkGreaterEqual(cols, 1, "cols");
		checkGreaterEqual(rows, 1, "rows");
		this.tileElements = new TileElement[rows][cols];
		this.emptyElement = new EmptyTileElement(cols, rows);
		this.emptyElement.setPosition(Position.ZERO);
		fillAreaWithEmptySpace(Position.ZERO, cols, rows);
	}

	public int getPurchasePrice() {
		return price;
	}

	public int getRows() {
		return this.tileElements.length;
	}

	public int getCols() {
		return this.tileElements[0].length;
	}

	public boolean canAddTileElement(TileElement element, Position position) {
		return canAddTileElementByDimension(element.getWidth(), element
				.getHeight(), position);
	}

	public boolean canAddTileElementByDimension(int width, int height,
			Position position) {
		return isAreaInsideBounds(width, height, position)
				&& isAreaEmpty(position, width, height);
	}

	public boolean isAreaInsideBounds(int width, int height, Position position) {
		checkGreaterEqual(width, 0, "width");
		checkGreaterEqual(height, 0, "height");
		boolean insideBounds = 0 <= position.getRow()
				&& position.getRow() + height <= this.getRows()
				&& 0 <= position.getCol()
				&& position.getCol() + width <= this.getCols();
		return insideBounds;
	}

	public void addTileElement(TileElement element, Position position) {
		checkNotNull(element, "tile element");
		checkArgCondition(element, canAddTileElement(element, position),
				"cannot add element at given position " + position);

		fillAreaWithElement(element, position, element.getWidth(), element
				.getHeight());

		element.setPosition(position);
	}

	public void removeTileElement(TileElement element) {
		checkNotNull(element, "tile element");
		Position position = element.getPosition();
		checkArgCondition(element, getTileElementAt(position).equals(element),
				"element is not in ground");
		
		fillAreaWithEmptySpace(position, element.getWidth(), element.getHeight());
		element.setPosition(TileElement.NOWHERE);
	}

	public void visitElements(TileElementVisitor visitor) {
		List<TileElement> visitedElements = new ArrayList<TileElement>();
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				TileElement element = getTileElement(row, col);
				if (!visitedElements.contains(element)) {
					element.accept(visitor);
					visitedElements.add(element);
				}
			}
		}
	}

	/**
	 * Retrieves the tile element at a given position. If position is out of
	 * ground bounds, this method returns an EmptyTileElement.
	 * 
	 * @param position
	 * @return
	 */
	public TileElement getTileElementAt(Position position) {
		if (!isAreaInsideBounds(1, 1, position))
			return this.emptyElement;
		return getTileElement(position.getRow(), position.getCol());
	}

	private boolean isAreaEmpty(Position position, int width, int height) {
		checkGreaterEqual(width, 0, "width");
		checkGreaterEqual(height, 0, "height");
		Position max = position.add(new Position(height, width));

		for (int row = position.getRow(); row < max.getRow(); row++)
			for (int col = position.getCol(); col < max.getCol(); col++)
				if (!isTileEmpty(row, col))
					return false;

		return true;
	}

	private boolean isTileEmpty(int row, int col) {
		return getTileElement(row, col) == this.emptyElement;
	}

	private TileElement getTileElement(int row, int col) {
		return this.tileElements[row][col];
	}

	private void fillAreaWithEmptySpace(Position from, int width, int height) {
		fillAreaWithElement(this.emptyElement, from, width, height);
	}

	private void fillAreaWithElement(TileElement element, Position position,
			int width, int height) {
		Position max = position.add(new Position(height, width));

		for (int row = position.getRow(); row < max.getRow(); row++)
			for (int col = position.getCol(); col < max.getCol(); col++)
				setTileElement(element, row, col);
	}

	private void setTileElement(TileElement element, int row, int col) {
		this.tileElements[row][col] = element;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + price;
		result = prime * result + Arrays.hashCode(tileElements);
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
		Ground other = (Ground) obj;
		if (price != other.price)
			return false;
		if (this.getRows() != other.getRows())
			return false;
		if (this.getCols() != other.getCols())
			return false;
		return true;
	}

	public int getSalePrice() {
		return (int) (getPurchasePrice() * GROUND_SALE_PRICE_COEF);
	}

	public int getRentPrice() {
		return (int) (getPurchasePrice() * GROUND_RENT_PRICE_COEF);
	}
}
