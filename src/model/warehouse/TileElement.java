package model.warehouse;

import static model.utils.ArgumentUtils.*;

abstract public class TileElement {

	private int width;
	private int height;
	private Position position;

	public TileElement(int width, int height) {
		setWidth(width);
		setHeight(height);
		setPosition(Position.ZERO);
	}

	public abstract void accept(TileElementVisitor visitor);

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Position getPosition() {
		return position;
	}

	private void setHeight(int height) {
		checkGreaterEqual(height, 1, "height");
		this.height = height;
	}

	private void setWidth(int width) {
		checkGreaterEqual(width, 1, "width");
		this.width = width;
	}

	private void setPosition(Position position) {
		checkNotNull(position, "position");
		this.position = position;
	}

	public void addToGround(Ground ground, Position position) {
		checkNotNull(ground, "ground");
		setPosition(position);
	}
}
