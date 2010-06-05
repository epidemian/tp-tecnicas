package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;
import model.exception.BusinessLogicException;

public class Ground {
	private int price;
	private TileElement tileElements[][];

	private int rows;
	private int cols;

	public Ground(int price, int rows, int cols) {
		this.price = price;
		this.setRows(rows);
		this.setCols(cols);
		this.tileElements = new TileElement[rows][cols];
	}

	public int getPrice() {
		return price;
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}

	/**
	 * 
	 * @deprecated Use isTileEmpty(Position) instead
	 */
	@Deprecated
	private boolean isTileEmpty(int row, int col) {
		return this.tileElements[row][col] == null;
	}

	/**
	 * 
	 * @deprecated Use isAreaEmpty(Position, width, height) or
	 *             isAreaEmpty(Position, Dimension)... or isAreaEmpty(Area)
	 */
	@Deprecated
	public boolean isAreaEmpty(int row, int col, int width, int heigh) {

		for (int j = col; j < col + width; j++)
			for (int i = row; i < row + heigh; i++)
				if (!isTileEmpty(i, j))
					return false;

		return true;
	}

	public void addTileElement(TileElement element, Position position) {

		if (!this.isAreaEmpty(position.getRow(), position.getCol(), element
				.getWidth(), element.getHeight())) {
			throw new BusinessLogicException(
					"Can not add tile element to the ground");
		}

		for (int col = position.getCol(); col < position.getCol()
				+ element.getWidth(); col++)
			for (int row = position.getRow(); row < position.getRow()
					+ element.getHeight(); row++) {
				this.tileElements[row][col] = element;
			}
	}

	public void visitElements(GroundVisitor visitor) {

		for (int col = 0; col < this.cols; col++)
			for (int row = 0; row < this.rows; row++) {
				visitor.setCurrentPosition(new Position(row, col));
				TileElement element = this.tileElements[row][col];
				if (element != null)
					element.accept(visitor);
			}
	}

	private void setRows(int rows) {
		checkGreaterEqual(rows, 1, "rows");
		this.rows = rows;
	}

	private void setCols(int cols) {
		checkGreaterEqual(cols, 1, "cols");
		this.cols = cols;
	}
}
