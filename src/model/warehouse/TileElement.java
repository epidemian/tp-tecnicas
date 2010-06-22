package model.warehouse;

import static model.utils.ArgumentUtils.*;

import org.hamcrest.core.IsNot;

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

	/**
	 * Retrieves the current position of the tile element on a ground. If the
	 * element is not in a ground (i.e: {@link TileElement#isNowhere()} yields
	 * true), this method throws an exception.
	 * 
	 * @return
	 */
	public Position getPosition() {
		if (isNowhere())
			throw new BusinessLogicException(
					"Cannot getPosition() if element isNowhere()");
		return position;
	}

	/**
	 * Returns true if the tile element has been added to a ground (and not
	 * removed from it yet). 
	 * @return
	 */
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
	 * Note: Should only be called by
	 * {@link Ground#addTileElement(TileElement, Position)}
	 * 
	 * @param position
	 */
	void setPosition(Position position) {
		checkNotNull(position, "position");
		this.position = position;
	}

	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}

}
