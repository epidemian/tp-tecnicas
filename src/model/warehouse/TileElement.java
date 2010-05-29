package model.warehouse;

public interface TileElement {
	
	public void accept(TileElementVisitor visitor);
	
	// TODO: haría falta esto?
	// public Tile getTile() o getPosition() ?
}
