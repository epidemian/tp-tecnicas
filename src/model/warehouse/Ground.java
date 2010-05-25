package model.warehouse;

public class Ground {
	private float price;
	private Tile groundTiles[][];
	
	private int rows;
	private int cols;
	
	public Ground(float price, int rows, int cols){
		this.price = price;
		this.groundTiles = new Tile[rows][cols];
	}
	
	public float getPrice() {
		return price;
	}
	
	public Tile getTile(int row, int col){
		return this.groundTiles[row][col];
	}
	
	public int getRows(){
		return this.rows;
	}
	
	public int getCols(){
		return this.cols;
	}
}
