package model.warehouse;

import static model.utils.ArgumentUtils.*;

import java.util.Arrays;

public class Ground {
	private int price;
	private TileElement tileElements[][];

	public Ground(int price, int rows, int cols) {
		this.price = price;
		checkGreaterEqual(cols, 1, "cols");
		checkGreaterEqual(rows, 1, "rows");
		this.tileElements = new TileElement[rows][cols];
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
	

	public boolean isAreaEmpty(Position pos, int width, int height) {

		for (int row = pos.getRow(); row < pos.getRow() + height; row++)
			for (int col = pos.getCol(); col < pos.getCol() + width; col++)
				if (!isTileEmpty(row, col))
					return false;

		return true;
	}

	public void addTileElement(TileElement element, Position position) {
		checkNotNull(element, "tile element");
		checkArgCondition(element, isAreaEmpty(position, element.getWidth(),
				element.getHeight()), "area is not empty");

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

	private boolean isTileEmpty(int row, int col) {
		return getTileElement(row, col).equals(EmptyTileElement.getInstance());
	}

	private TileElement getTileElement(int row, int col) {
		return this.tileElements[row][col];
	}

	
	/*
	private void fillAreaWithEmptySpace(Position from, int width, int height) {
		fillAreaWithElement(EmptyTileElement.getInstance(), from, width, height);
	}

	
	private void setTileElement(TileElement element, int col, int row) {
		this.tileElements[row][col] = element;
	}
	
	private void fillAreaWithElement(TileElement element, Position position,
			int width, int height) {
		Position max = position.add(new Position(width, height));
		for (int row = position.getRow(); row < max.getRow(); row++)
			for (int col = position.getCol(); col < max.getCol(); col++)
				setTileElement(element, col, row);
	}
	*/
	

	private void fillAreaWithEmptySpace(Position from, int width, int height) {
		fillAreaWithElement(EmptyTileElement.getInstance(), from, height, width);
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
