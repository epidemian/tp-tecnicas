package model.warehouse;

public class Wall extends TileElement {

	public Wall(int width, int height) {
		super(width, height);
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitWall(this);
	}

	public boolean equals(Object obj){		
		return (getClass() == obj.getClass());
	}
}
