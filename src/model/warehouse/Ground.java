package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;

import java.awt.Dimension;

public class Ground {
	private int price;
	private Tile groundTiles[][];

	private int rows;
	private int cols;

	public Ground(int price, int rows, int cols) {
		this.price = price;
		this.setRows(rows);
		this.setCols(cols);
		this.groundTiles = new Tile[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				groundTiles[i][j] = new Tile();
			}
		}
	}

	public int getPrice() {
		return price;
	}

	@Deprecated
	public Tile getTile(int row, int col) {
		return this.groundTiles[row][col];
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}

	private boolean isTileEmpty(int row, int col) {
		return this.groundTiles[row][col].getTileElement() == null;
	}

	public boolean isAreaEmpty(int row, int col, int width, int heigh) {

		for (int j = col; j < col + width; j++)
			for (int i = row; i < row + heigh; i++)
				if (!isTileEmpty(i, j))
					return false;

		return true;
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
