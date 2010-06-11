package model.production;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.exception.BusinessLogicException;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class Conveyor extends ProductionLineElement {

	public enum Direction {
		NORTH, EAST, SOUTH, WEST, NONE;
	}

	private Direction previousLineElementDirection = Direction.NONE;
	private Direction nextLineElementDirection = Direction.NONE;

	/**
	 * TODO: Pass next and prev line element direction (or position)
	 */
	public Conveyor() {
		super(1, 1);
	}

	public Direction getPreviousLineElementDirection() {
		return previousLineElementDirection;
	}

	public Direction getNextLineElementDirection() {
		return nextLineElementDirection;
	}
	
	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitConveyor(this);
	}

	@Override
	public String toString() {
		return "Conveyor";
	}
}
