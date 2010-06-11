package model.warehouse;

public class EmptyTileElement extends TileElement {

	private static final EmptyTileElement INSTANCE = new EmptyTileElement();

	private EmptyTileElement() {
		super(1, 1);
	}

	public static EmptyTileElement getInstance() {
		return INSTANCE;
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
