package model.warehouse;

public class Tile {

	private TileElement element;

	public Tile(TileElement element) {
		this.element = element;
	}

	public Tile() {
		this.element = null;
	}

	public TileElement getTileElement() {
		return element;
	}

	public void setTileElement(TileElement element) {
		this.element = element;
	}	
}
