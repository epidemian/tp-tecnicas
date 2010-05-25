package model.warehouse;

public class Ground {
	
	private float price;
	private Tile groundTiles[][];
	
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
	
	public void setTile(Tile tile, int row, int col){
		this.groundTiles[row][col] = tile;
	}	
}
