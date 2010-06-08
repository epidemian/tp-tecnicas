package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;

abstract public class TileElement {

	private int width;
	private int height;
	
	public TileElement(int width, int height){
		this.setWidth(width);
		this.setHeight(height);
	}
		
	public abstract void accept(TileElementVisitor visitor);

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	private void setHeight(int height) {
		checkGreaterEqual(height, 1, "height");
		this.height = height;
	}

	private void setWidth(int width) {
		checkGreaterEqual(width, 1, "width");
		this.width = width;
	}
}
