package view.game.edition.tools;

import static model.production.ProductionLineElement.connectLineElements;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.exception.BusinessLogicException;
import model.game.Game;
import model.production.Conveyor;
import model.production.Direction;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.game.GamePanel;
import view.game.GroundPainter;
import view.game.GroundPanel;
import view.game.edition.EditionTool;

public class AddConveyorTool extends EditionTool {

	private enum EdgeType {
		INPUT, OUTPUT
	}

	private List<Position> conveyorPositions = new ArrayList<Position>();
	EdgeType initialEdgeType = null;
	ProductionLineElement initialElement = null;

	public AddConveyorTool(GamePanel gamePanel, Game game) {
		super(gamePanel, game);
	}

	@Override
	public void mouseClicked(Position mousePosition) {
		ProductionLineElement element = getLineElementAt(mousePosition);
		if (element != null)
			clickedOverLineElement(element, mousePosition);
		else
			clickedOverNonLineElement(mousePosition);
	}

	private void clickedOverLineElement(ProductionLineElement element,
			Position mousePosition) {
		if (isBuilding()) {
			Position lastPosition = computeLastPosition(element, mousePosition);
			boolean couldAddSegment = addNewConveyorSegment(lastPosition);
			if (couldAddSegment)
				finishBuildingConveyor(element);
		} else
			startBuildingConveyorIfPossible(element, mousePosition);
	}

	private void finishBuildingConveyor(ProductionLineElement lastElement) {

		ProductionLineElement firstElement = this.initialElement;

		// Orders the positions and alters the first and last elements so the
		// position list goes from an output to an input.
		if (this.initialEdgeType == EdgeType.INPUT) {
			firstElement = lastElement;
			lastElement = this.initialElement;
			Collections.reverse(this.conveyorPositions);
		}

		Position prevThanFirstPosition = getPreviousPositionThanOutput(firstElement);
		Position nextThanLastPosition = getNextPositionThanInput(lastElement);

		Conveyor prevConveyor = null;
		int nConveyors = this.conveyorPositions.size();
		for (int i = 0; i < nConveyors; i++) {
			Position currPos = this.conveyorPositions.get(i);
			Position prevPos = i == 0 ? prevThanFirstPosition
					: this.conveyorPositions.get(i - 1);
			Position nextPos = i == nConveyors - 1 ? nextThanLastPosition
					: this.conveyorPositions.get(i + 1);
			ProductionLineElement prevElement = i == 0 ? firstElement
					: prevConveyor;

			Conveyor conveyor = createConveyor(currPos, prevPos, nextPos);
			connectLineElements(prevElement, conveyor);
			buyAndAddConveyor(conveyor, currPos);

			prevConveyor = conveyor;
		}
		connectLineElements(prevConveyor, lastElement);
		updateBudgetView();
	}

	private void updateBudgetView() {
		int balance = this.getGame().getBudget().getBalance();
		this.getGamePanel().getBudgetPanel().setMoneyBalance(balance);
	}

	private void buyAndAddConveyor(Conveyor conveyor, Position position) {
		this.getGame().buyAndAddProductionLineElement(conveyor, position);
	}

	private Conveyor createConveyor(Position currPos, Position prevPos,
			Position nextPos) {
		Position prevDiff = prevPos.subtract(currPos);
		Position nextDiff = nextPos.subtract(currPos);
		Direction inputDir = Direction.getDirectionByPosition(prevDiff);
		Direction outputDir = Direction.getDirectionByPosition(nextDiff);
		return new Conveyor(inputDir, outputDir);
	}

	private Position getPreviousPositionThanOutput(
			ProductionLineElement firstElement) {
		Direction outputDir = firstElement.getOutputConnectionDirection();
		return firstElement.getPosition().subtract(
				outputDir.getAssociatedPosition());
	}

	private Position getNextPositionThanInput(ProductionLineElement lastElement) {
		Direction inputDir = lastElement.getInputConnectionDirection();
		return lastElement.getPosition().subtract(
				inputDir.getAssociatedPosition());
	}

	private Position computeLastPosition(ProductionLineElement element,
			Position defaultPosition) {
		Position lastPosition = defaultPosition;
		if (initialEdgeType == EdgeType.INPUT && isOutputAvailable(element))
			lastPosition = element.getOutputConnectionPosition();
		else if (initialEdgeType == EdgeType.OUTPUT
				&& isInputAvailable(element))
			lastPosition = element.getInputConnectionPosition();
		return lastPosition;
	}

	private void startBuildingConveyorIfPossible(ProductionLineElement element,
			Position mousePosition) {
		EdgeType from = getNearestAvailableConnectionEdgeType(element,
				mousePosition);
		if (from != null) {
			Position firstPos = getConnectionPositionByEdgeType(element, from);
			this.conveyorPositions.add(firstPos);
			this.initialEdgeType = from;
			this.initialElement = element;
		}
	}

	private void clickedOverNonLineElement(Position mousePosition) {
		if (isBuilding())
			addNewConveyorSegment(mousePosition);
	}

	private boolean addNewConveyorSegment(Position position) {
		List<Position> newPositions = createPositionsTo(position);
		if (areAllTilesEmpty(newPositions)) {
			this.conveyorPositions.addAll(newPositions);
			return true;
		} else
			return false;
	}

	private Position getConnectionPositionByEdgeType(
			ProductionLineElement element, EdgeType edgeType) {
		switch (edgeType) {
		case INPUT:
			return element.getInputConnectionPosition();
		case OUTPUT:
			return element.getOutputConnectionPosition();
		default:
			throw new BusinessLogicException("Invalid edge type");
		}
	}

	private EdgeType getNearestAvailableConnectionEdgeType(
			ProductionLineElement element, Position mousePos) {
		boolean inputAvailable = isInputAvailable(element);
		boolean outputAvailable = isOutputAvailable(element);

		EdgeType edgeType;
		if (inputAvailable && outputAvailable)
			edgeType = getNearestConnectionEdgeType(element, mousePos);
		else if (inputAvailable)
			edgeType = EdgeType.INPUT;
		else if (outputAvailable)
			edgeType = EdgeType.OUTPUT;
		else
			edgeType = null;

		return edgeType;
	}

	private boolean isInputAvailable(ProductionLineElement element) {
		return !element.hasPreviousLineElement()
				&& !isTileEmpty(element.getInputConnectionPosition());
	}

	private boolean isOutputAvailable(ProductionLineElement element) {
		return !element.hasNextLineElement()
				&& !isTileEmpty(element.getOutputConnectionPosition());
	}

	private EdgeType getNearestConnectionEdgeType(
			ProductionLineElement element, Position mousePos) {
		Position inputPos = element.getInputConnectionPosition();
		Position outputPos = element.getOutputConnectionPosition();
		int inputDistance = mousePos.squareDistance(inputPos);
		int outputDistance = mousePos.squareDistance(outputPos);

		return inputDistance < outputDistance ? EdgeType.INPUT
				: EdgeType.OUTPUT;
	}

	private boolean areAllTilesEmpty(List<Position> positions) {
		for (Position pos : positions)
			if (!isTileEmpty(pos))
				return false;
		return true;
	}

	private List<Position> createPositionsTo(Position position) {
		Position last = lastPosition();
		Position diff = position.subtract(last);

		int deltaRow = (int) Math.signum(diff.getRow());
		int deltaCol = (int) Math.signum(diff.getCol());

		List<Position> positions = new ArrayList<Position>();
		for (int row = last.getRow(); row != position.getRow(); row += deltaRow)
			positions.add(new Position(row, last.getCol()));

		last = positions.get(positions.size() - 1);
		for (int col = last.getCol(); col != position.getCol(); col += deltaCol)
			positions.add(new Position(last.getRow(), col));

		return positions;
	}

	private Position lastPosition() {
		return this.conveyorPositions.get(this.conveyorPositions.size() - 1);
	}

	@Override
	public void paint(Graphics2D graphics) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		this.conveyorPositions.clear();
		this.initialEdgeType = null;
		this.initialElement = null;
	}

	private boolean isTileEmpty(Position position) {
		return this.getGround().canPutTileElementByDimension(1, 1, position);
	}

	private boolean isBuilding() {
		return !this.conveyorPositions.isEmpty();
	}

	private GroundPainter getPainter() {
		GroundPainter painter = this.getGroundPanel().getPainter();
		return painter;
	}

	private Ground getGround() {
		return this.getGame().getGround();
	}

	private GroundPanel getGroundPanel() {
		return this.getGamePanel().getGroundPanelContainer().getGroundPanel();
	}

	private ProductionLineElement getLineElementAt(Position mousePosition) {
		return recognizeLineElement(getGround().getTileElementAt(mousePosition));
	}

	private static ProductionLineElement recognizeLineElement(
			TileElement tileElement) {
		return LineElementRecognizer.recognizeLineElement(tileElement);
	}
}

class LineElementRecognizer extends TileElementVisitor {

	private static final LineElementRecognizer INSTANCE = new LineElementRecognizer();

	private LineElementRecognizer() {
	}

	private ProductionLineElement lineElement;

	public static ProductionLineElement recognizeLineElement(
			TileElement tileElement) {
		INSTANCE.lineElement = null;
		tileElement.accept(INSTANCE);
		return INSTANCE.lineElement;
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.lineElement = conveyor;
	}

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		this.lineElement = machine;
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		this.lineElement = machine;
	}

}
