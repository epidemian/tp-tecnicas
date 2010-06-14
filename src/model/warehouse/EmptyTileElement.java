package model.warehouse;

public class EmptyTileElement extends TileElement {

	public EmptyTileElement(int width, int height) {
		super(width, height);
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitEmptyElement(this);
	}

	@Override
	public String toString() {
		return "Empty";
	}
	
	public boolean equals(Object obj){		
		return (getClass() == obj.getClass());
	}

}
