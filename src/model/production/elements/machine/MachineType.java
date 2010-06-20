package model.production.elements.machine;

import static model.utils.ArgumentUtils.*;

import model.exception.BusinessLogicException;
import model.production.AbstractType;
import model.production.Direction;
import model.production.elements.machine.states.BrokenMachineState;
import model.production.elements.machine.states.DamagedMachineState;
import model.warehouse.Position;

public class MachineType extends AbstractType {

	private final int height;
	private final int width;
	private final int price;

	private final Position inputRelativePosition;
	private final Position outputRelativePosition;

	// the probability that the machine will be damaged or broken after
	// processing
	private final float breakChance;
	private final float damageChance;

	private MachineType(String name, int width, int height,
			Position inputRelativePosition, Position outputRelativePosition,
			float breakChance, float damageChance, int price) {
		super(name);
		this.height = height;
		this.width = width;

		checkGreaterEqual(price, 0, "price");
		this.price = price;

		checkInputAndOutputPositions(inputRelativePosition,
				outputRelativePosition);
		this.inputRelativePosition = inputRelativePosition;
		this.outputRelativePosition = outputRelativePosition;

		this.breakChance = breakChance;
		this.damageChance = damageChance;
	}

	private void checkInputAndOutputPositions(Position inputPosition,
			Position outputPosition) {
		if (inputPosition.equals(outputPosition))
			throw new BusinessLogicException(
					"Input and output positions must not be equal");
		
		String wAndH = "width " + width + " height " + height;
		checkArgCondition(inputPosition,
				isValidInputOrOutputPosition(inputPosition),
				"Invalid input position " + wAndH);
		checkArgCondition(outputPosition,
				isValidInputOrOutputPosition(outputPosition),
				"Invalid output position " + wAndH);
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

	public float getBreakChance() {
		return breakChance;
	}

	public float getDamageChance() {
		return damageChance;
	}

	public static class Builder {
		
		private static final int DEFAULT_MACHINE_PRICE = 0;
		private static final float DEFAULT_BREAK_MACHINE_CHANCE = 0.02f;
		private static final float DEFAULT_DAMAGE_MACHINE_CHANCE = 0.02f;
		
		private String name;
		private int width;
		private int height;
		private int price;
		
		private Position inputRelativePosition;
		private Position outputRelativePosition;

		private float breakChance;
		private float damageChance;

		public Builder(String name, int width, int height) {
			this.name = name;
			this.width = width;
			this.height = height;
			
			this.price = DEFAULT_MACHINE_PRICE;
			this.breakChance = DEFAULT_BREAK_MACHINE_CHANCE;
			this.damageChance = DEFAULT_DAMAGE_MACHINE_CHANCE;
			
			int col = height / 2; 
			this.inputRelativePosition = new Position(col, -1); 
			this.outputRelativePosition = new Position(col, width);
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder width(int width) {
			this.width = width;
			return this;
		}
		
		public Builder height(int height) {
			this.height = height;
			return this;
		}
		
		public Builder price(int price) {
			this.price = price;
			return this;
		}

		public Builder inputRelativePosition(Position inputRelativePosition) {
			this.inputRelativePosition = inputRelativePosition;
			return this;
		}

		public Builder outputRelativePosition(Position outputRelativePosition) {
			this.outputRelativePosition = outputRelativePosition;
			return this;
		}

		public Builder breakChance(float breakChance) {
			this.breakChance = breakChance;
			return this;
		}

		public Builder damageChance(float damageChance) {
			this.damageChance = damageChance;
			return this;
		}

		public MachineType build() {
			return new MachineType(name, width, height, inputRelativePosition,
					outputRelativePosition, breakChance, damageChance, price);
		}
	}

}
