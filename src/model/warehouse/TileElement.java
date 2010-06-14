package model.warehouse;

import static model.utils.ArgumentUtils.*;
import model.exception.BusinessLogicException;

abstract public class TileElement {

	static final Position NOWHERE = new Position(-1, -1);

	private int width;
	private int height;
	private Position position;

	public TileElement(int width, int height) {
		setWidth(width);
		setHeight(height);
		setPosition(NOWHERE);
	}

	public abstract void accept(TileElementVisitor visitor);

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Position getPosition() {
		if (isNowhere())
			throw new BusinessLogicException(
					"Cannot getPosition() if element isNowhere()");
		return position;
	}

	public boolean isNowhere() {
		return this.position.equals(NOWHERE);
	}

	private void setHeight(int height) {
		checkGreaterEqual(height, 1, "height");
		this.height = height;
	}

	private void setWidth(int width) {
		checkGreaterEqual(width, 1, "width");
		this.width = width;
	}

	/**
	 * Note: Should only be called by {@link Ground#putTileElement(TileElement, Position)}
	 * @param position
	 */
	void setPosition(Position position) {
		checkNotNull(position, "position");
		this.position = position;
	}

}
