package model.warehouse;

import static model.utils.ArgumentUtils.*;

import java.util.Arrays;

public class Ground {
	private int price;
	private TileElement tileElements[][];
	private EmptyTileElement emptyElement;

	public Ground(int price, int rows, int cols) {
		this.price = price;
		checkGreaterEqual(cols, 1, "cols");
		checkGreaterEqual(rows, 1, "rows");
		this.tileElements = new TileElement[rows][cols];
		this.emptyElement = new EmptyTileElement(cols, rows);
		this.emptyElement.setPosition(Position.ZERO);
		fillAreaWithEmptySpace(Position.ZERO, rows, cols);
	}

	public int getPrice() {
		return price;
	}

	public int getRows() {
		return this.tileElements.length;
	}

	public int getCols() {
		return this.tileElements[0].length;
	}

	public boolean canPutTileElement(TileElement element, Position position) {
		return canPutTileElementByDimension(element.getWidth(), element
				.getHeight(), position);
	}

	public boolean canPutTileElementByDimension(int width, int height,
			Position position) {
		checkGreaterEqual(width, 0, "width");
		checkGreaterEqual(height, 0, "height");
		boolean insideBounds = 0 <= position.getRow()
				&& position.getRow() + height <= this.getRows()
				&& 0 <= position.getCol()
				&& position.getCol() + width <= this.getCols();

		return insideBounds && isAreaEmpty(position, width, height);
	}

	public void putTileElement(TileElement element, Position position) {
		checkNotNull(element, "tile element");
		checkArgCondition(element, canPutTileElement(element, position),
				"cannot put element at given position " + position);

		fillAreaWithElement(element, position, element.getWidth(), element
				.getHeight());

		element.setPosition(position);
	}

	public void visitElements(TileElementVisitor visitor) {
		for (int row = 0; row < getRows(); row++)
			for (int col = 0; col < getCols(); col++)
				getTileElement(row, col).accept(visitor);
	}

	public TileElement getTileElementAt(Position position) {
		return getTileElement(position.getRow(), position.getCol());
	}

	private boolean isAreaEmpty(Position position, int width, int height) {
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
		fillAreaWithElement(this.emptyElement, from, height, width);
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
}
