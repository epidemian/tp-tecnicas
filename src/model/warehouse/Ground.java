package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;
import model.exception.BusinessLogicException;

public class Ground {
	private int price;
	private TileElement tileElements[][];

	public Ground(int price, int rows, int cols) {
		this.price = price;
		checkGreaterEqual(cols, 1, "cols");
		checkGreaterEqual(rows, 1, "rows");
		this.tileElements = new TileElement[rows][cols];
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

	public boolean isAreaEmpty(Position pos, int width, int heigh) {

		for (int row = pos.getRow(); row < pos.getRow() + heigh; row++)
			for (int col = pos.getCol(); col < pos.getCol() + width; col++)
				if (!isTileEmpty(row, col))
					return false;
		
		return true;
	}

	public void addTileElement(TileElement element, Position position) {

		if (!isAreaEmpty(position, element.getWidth(), element.getHeight())) {
			throw new BusinessLogicException(
					"Can not add tile element to the ground");
		}

		Position max = position.add(new Position(element.getHeight(), element
				.getWidth()));
		for (int row = position.getRow(); row < max.getRow(); row++)
			for (int col = position.getCol(); col < max.getCol(); col++)
				setTileElement(element, col, row);
	}

	public void visitElements(GroundVisitor visitor) {

		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				visitor.setCurrentPosition(new Position(row, col));
				if (!isTileEmpty(row, col))
					getTileElement(row, col).accept(visitor);
			}
		}
	}

	private boolean isTileEmpty(int row, int col) {
		return getTileElement(row, col) == null;
	}

	private TileElement getTileElement(int row, int col) {
		return this.tileElements[row][col];
	}

	private void setTileElement(TileElement element, int col, int row) {
		this.tileElements[row][col] = element;
	}

}
