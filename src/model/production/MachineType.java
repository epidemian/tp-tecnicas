package model.production;

import static model.utils.ArgumentUtils.checkGreaterEqual;
import model.warehouse.Position;

public class MachineType extends AbstractType {

	private int height;
	private int width;
	private int price;

	private Position inputRelativePosition;
	private Position outputRelativePosition;

	/*
	 * TODO borrar!
	 */
	public MachineType(String name) {
		this(name, 1, 1);
	}

	/*
	 * TODO borrar!
	 */
	public MachineType(String name, int width, int height) {
		this(name, width, height, 0);
	}

	public MachineType(String name, int width, int height, int price) {
		super(name);
		checkGreaterEqual(height, 1, "height");
		checkGreaterEqual(width, 1, "width");
		this.height = height;
		this.width = width;
		this.price = price;

		// TODO: This is hard-coded, pass positions as parameters.
		this.inputRelativePosition = new Position(0, -1);
		this.outputRelativePosition = new Position(0, width);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPrice() {
		return price;
	}

	public Position getInputRelativePosition() {
		return inputRelativePosition;
	}

	public Position getOutputRelativePosition() {
		return outputRelativePosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
		result = prime * result + price;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineType other = (MachineType) obj;
		if (height != other.height)
			return false;
		if (price != other.price)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
