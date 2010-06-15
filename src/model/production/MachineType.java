package model.production;

import static model.utils.ArgumentUtils.*;

import model.exception.BusinessLogicException;
import model.warehouse.Position;

public class MachineType extends AbstractType {

	private int height;
	private int width;
	private int price;

	private Position inputRelativePosition;
	private Position outputRelativePosition;

	public static final int DEFECT_MACHINE_PRICE = 0;

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
		this(name, width, height, new Position(0, -1), new Position(0, width),
				MachineType.DEFECT_MACHINE_PRICE);
	}

	public MachineType(String name, int width, int height,
			Position inputRelativePosition, Position outputRelativePosition,
			int price) {
		super(name);
		this.height = height;
		this.width = width;

		checkGreaterEqual(price, 0, "price");
		this.price = price;

		checkInputAndOutputPositions(inputRelativePosition,
				outputRelativePosition);
		this.inputRelativePosition = inputRelativePosition;
		this.outputRelativePosition = outputRelativePosition;
	}

	private void checkInputAndOutputPositions(Position inputPosition,
			Position outputPosition) {
		if (inputPosition.equals(outputPosition))
			throw new BusinessLogicException(
					"Input and output positions must not be equal");
		checkArgCondition(inputPosition,
				isValidInputOrOutputPosition(inputPosition),
				"Invalid input position");
		checkArgCondition(outputPosition,
				isValidInputOrOutputPosition(outputPosition),
				"Invalid output position");
	}

	/*
	 * public static MachineType
	 * createMachineTypeWithPriceAndInputOutputPositions (String name, int
	 * height, int width, int price, Position inputRelativePosition, Position
	 * outputRelativePosition){ MachineType type = new
	 * MachineType(name,height,width,price,inputRelativePosition,
	 * outputRelativePosition); return type;
	 * 
	 * }
	 * 
	 * public static MachineType createMachineTypeInputOutputPositions (String
	 * name, int height, int width, Position inputRelativePosition, Position
	 * outputRelativePosition){ MachineType type = new
	 * MachineType(name,height,width,0,inputRelativePosition,
	 * outputRelativePosition); return type; }
	 */

	/**
	 * Returns true if position is in the "surroundings" of the machine area.
	 * Graphically, position P can be:
	 * 
	 * <pre>
	 *    PPP
	 *   PMMMP
	 *   PMMMP
	 *    PPP
	 * </pre>
	 * 
	 * Where M are are the tiles occupied by the machine =P, and the top-left M
	 * is (0,0).
	 */
	private boolean isValidInputOrOutputPosition(Position position) {

		int row = position.getRow();
		int col = position.getCol();

		if (row == -1 || row == this.getHeight())
			return 0 <= col && col < this.getWidth();
		else if (col == -1 || col == this.getWidth())
			return 0 <= row && row < this.getHeight();
		else
			return false;
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
		result = prime
				* result
				+ ((inputRelativePosition == null) ? 0 : inputRelativePosition
						.hashCode());
		result = prime
				* result
				+ ((outputRelativePosition == null) ? 0
						: outputRelativePosition.hashCode());
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
		if (inputRelativePosition == null) {
			if (other.inputRelativePosition != null)
				return false;
		} else if (!inputRelativePosition.equals(other.inputRelativePosition))
			return false;
		if (outputRelativePosition == null) {
			if (other.outputRelativePosition != null)
				return false;
		} else if (!outputRelativePosition.equals(other.outputRelativePosition))
			return false;
		if (price != other.price)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	public Direction getInputConnectionDirection() {
		return getInOutDirectionByPosition(this.getInputRelativePosition());
	}

	public Direction getOutputConnectionDirection() {
		return getInOutDirectionByPosition(this.getOutputRelativePosition());
	}
	
	private Direction getInOutDirectionByPosition(Position position) {
		if (position.getRow() == -1)
			return Direction.NORTH;
		if (position.getRow() == getHeight())
			return Direction.SOUTH;
		if (position.getCol() == -1)
			return Direction.WEST;
		if (position.getCol() == getWidth())
			return Direction.EAST;
		throw new BusinessLogicException("Invalid position");
	}

}
