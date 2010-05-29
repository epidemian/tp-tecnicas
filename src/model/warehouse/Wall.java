package model.warehouse;

public class Wall implements TileElement {

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitWall(this);
	}

}
