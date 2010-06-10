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

	private enum Direction {
		NORTH, EAST, SOUTH, WEST, NONE;
	}

	private Direction previousLineElementDirection = Direction.NONE;
	private Direction nextLineElementDirection = Direction.NONE;

	public Conveyor() {
		super(1, 1);
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitConveyor(this);
	}

	@Override
	public String toString() {
		return "Conveyor";
	}

	@Override
	protected boolean canConnectToByType(ProductionLineElement lineElement) {
		// A conveyor can connect to every lineElement
		return true;
	}

	@Override
	protected Collection<Position> getValidNextLineElementPositions() {
		return getValidAdyacentLineElementPositions();
	}

	@Override
	protected Collection<Position> getValidPreviousLineElementPositions() {
		return getValidAdyacentLineElementPositions();
	}

	private Collection<Position> getValidAdyacentLineElementPositions() {
		Collection<Position> positions = new ArrayList<Position>();
		for (Entry<Position, Direction> entry : DIFF_DIRECTION_MAP.entrySet())
			if (!entry.getValue().equals(this.previousLineElementDirection)
					&& !entry.getValue().equals(this.nextLineElementDirection))
				positions.add(getPosition().add(entry.getKey()));
		
		return positions;
	}

	@Override
	protected void setPreviousLineElement(
			ProductionLineElement previousLineElement) {
		super.setPreviousLineElement(previousLineElement);

		this.previousLineElementDirection = previousLineElement == null ? Direction.NONE
				: computePreviousLineElementDirection();
	}

	@Override
	protected void setNextLineElement(ProductionLineElement nextLineElement) {
		super.setNextLineElement(nextLineElement);

		this.nextLineElementDirection = nextLineElement == null ? Direction.NONE
				: computeNextLineElementDirection();
	}

	private Direction computePreviousLineElementDirection() {
		ProductionLineElement prevElement = getPreviousLineElement();
		// TODO: manage generic case.
		if (prevElement.getWidth() != 1 || prevElement.getHeight() != 1)
			throw new BusinessLogicException(
					"Connection to elements bigger than 1x1 not supported yet");

		return computeDirectionBy1x1ElementPosition(prevElement.getPosition());
	}

	private Direction computeNextLineElementDirection() {
		ProductionLineElement nextElement = getNextLineElement();
		if (nextElement.getWidth() != 1 || nextElement.getHeight() != 1)
			throw new BusinessLogicException(
					"Connection to elements bigger than 1x1 not supported yet");

		return computeDirectionBy1x1ElementPosition(nextElement.getPosition());
	}

	private static final Map<Position, Direction> DIFF_DIRECTION_MAP = createDiffDirectionMap();

	private static Map<Position, Direction> createDiffDirectionMap() {
		Map<Position, Direction> map = new HashMap<Position, Direction>();
		map.put(new Position(-1, 0), Direction.NORTH);
		map.put(new Position(1, 0), Direction.SOUTH);
		map.put(new Position(0, -1), Direction.WEST);
		map.put(new Position(0, 1), Direction.EAST);
		return map;
	}

	private Direction computeDirectionBy1x1ElementPosition(Position position) {
		Position diff = position.subtract(getPosition());

		if (DIFF_DIRECTION_MAP.containsKey(diff))
			return DIFF_DIRECTION_MAP.get(diff);
		else
			throw new BusinessLogicException("Invalid position");
	}
}
