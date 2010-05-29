package model.warehouse;

import java.util.Iterator;

public class Ground {
	private int price;
	private Tile groundTiles[][];

	private int rows;
	private int cols;

	public Ground(int price, int rows, int cols) {
		this.price = price;
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

	public Tile getTile(int row, int col) {
		return this.groundTiles[row][col];
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}
}
