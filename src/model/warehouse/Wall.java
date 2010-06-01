package model.warehouse;

public class Wall extends TileElement {

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitWall(this);
	}

}
