package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;

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

	public void addTileElement(TileElement element, Position position) {

		// TODO falta terminar
		this.isAreaEmpty(position.row, position.col, element.getWidth(),
				element.getHeight());
	}

	public void visitElements(GroundVisitor visitor){
		
		for (int col = 0; col < this.cols; col++)
			for (int row = 0; row < this.rows; row++) {
				visitor.getCurrentPosition().row = row;
				visitor.getCurrentPosition().col = col;
				
				TileElement element = this.groundTiles[row][col].getTileElement();
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
