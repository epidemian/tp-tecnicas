package view.game.edition.tools;

import static model.utils.StringUtils.join;
import static view.game.edition.tools.Colors.*;
import static model.production.ProductionLineElement.connectLineElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
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
		System.out.println("Clicked over line element");
		if (isBuilding()) {
			System.out.println("is building...");
			Position lastPosition = computeLastPosition(element, mousePosition);
			boolean couldAddSegment = addNewConveyorSegment(lastPosition);
			if (couldAddSegment) {
				finishBuildingConveyor(element);
				reset();
			}
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
		System.out.println("elements: " + firstElement + "  " + lastElement);
		System.out.println("prev than first " + prevThanFirstPosition);
		System.out.println("next than last " + nextThanLastPosition);
		System.out
				.println("in pos " + lastElement.getInputConnectionPosition());
		System.out.println("out pos "
				+ lastElement.getOutputConnectionPosition());

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
		System.out.println("Create conveyor curr: " + currPos + " prev + "
				+ prevPos + " nextPos " + nextPos);
		Position prevDiff = prevPos.subtract(currPos);
		Position nextDiff = nextPos.subtract(currPos);
		Direction inputDir = Direction.getDirectionByPosition(prevDiff);
		Direction outputDir = Direction.getDirectionByPosition(nextDiff);
		return new Conveyor(inputDir, outputDir);
	}

	private Position getPreviousPositionThanOutput(
			ProductionLineElement firstElement) {
		Direction outputDir = firstElement.getOutputConnectionDirection();
		return firstElement.getOutputConnectionPosition().subtract(
				outputDir.getAssociatedPosition());
	}

	private Position getNextPositionThanInput(ProductionLineElement lastElement) {
		Direction inputDir = lastElement.getInputConnectionDirection();
		return lastElement.getInputConnectionPosition().subtract(
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
		System.out.println("Start building conveyor if possible");
		EdgeType from = getNearestAvailableConnectionEdgeType(element,
				mousePosition);
		System.out.println("from: " + from);
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
		boolean canAdd = canAddConveyorPositions(newPositions);
		if (canAdd)
			this.conveyorPositions.addAll(newPositions);
		return canAdd;
	}

	private boolean canAddConveyorPositions(List<Position> positions) {
		for (Position pos : positions)
			if (!canAddConveyorPosition(pos))
				return false;
		return true;
	}

	private boolean canAddConveyorPosition(Position pos) {
		return isTileEmpty(pos) && !this.conveyorPositions.contains(pos);
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
		System.out.println("in out available: " + inputAvailable + " "
				+ outputAvailable);
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
				&& isTileEmpty(element.getInputConnectionPosition());
	}

	private boolean isOutputAvailable(ProductionLineElement element) {
		System.out.println(" has next " + element.hasNextLineElement());
		System.out.println(" isTileEmpty "
				+ isTileEmpty(element.getOutputConnectionPosition()));
		return !element.hasNextLineElement()
				&& isTileEmpty(element.getOutputConnectionPosition());
	}

	private EdgeType getNearestConnectionEdgeType(
			ProductionLineElement element, Position mousePos) {
		Position inputPos = element.getInputConnectionPosition();
		Position outputPos = element.getOutputConnectionPosition();
		int inputDistance = mousePos.squareDistance(inputPos);
		int outputDistance = mousePos.squareDistance(outputPos);
		System.out.println("distances, in: " + inputDistance + " out: "
				+ outputDistance);
		return inputDistance < outputDistance ? EdgeType.INPUT
				: EdgeType.OUTPUT;
	}

	private List<Position> createPositionsTo(Position position) {
		Position last = lastPosition();
		Position diff = position.subtract(last);

		int deltaRow = (int) Math.signum(diff.getRow());
		int deltaCol = (int) Math.signum(diff.getCol());
		int absRowDiff = Math.abs(diff.getRow());
		int absColDiff = Math.abs(diff.getCol());

		List<Position> positions = new ArrayList<Position>();

		int row = last.getRow();
		int col = last.getCol();

		for (int i = 1; i <= absRowDiff; i++) {
			row += deltaRow;
			positions.add(new Position(row, col));
		}
		for (int i = 1; i <= absColDiff; i++) {
			col += deltaCol;
			positions.add(new Position(row, col));
		}

		return positions;
	}

	private Position lastPosition() {
		return this.conveyorPositions.get(this.conveyorPositions.size() - 1);
	}

	@Override
	public void paint(Graphics2D graphics) {
		drawAllConveyorPositions(graphics);

		Position mousePosition = getGroundPanel().getCurrentMousePosition();
		if (mousePosition == null)
			return;
		
		ProductionLineElement element = getLineElementAt(mousePosition);

		List<Position> newPositions;
		List<String> warnings = new ArrayList<String>();
		if (element != null)
			newPositions = getNewPositionsWhenOverLineElement(mousePosition,
					element);
		else
			newPositions = getNewPositionsWhenOverNonLineElement(mousePosition);

		boolean canAddAllConveyors = true;
		for (Position pos : newPositions) {
			boolean canAddConveyor = canAddConveyorPosition(pos);
			Color color = canAddConveyor ? OK_COLOR : BAD_COLOR;
			drawConveyorRectangle(pos, color, graphics);
			if (!canAddConveyor && canAddAllConveyors)
				canAddAllConveyors = false;
		}
		if (!canAddAllConveyors)
			warnings.add("Bocked path");

		if (!warnings.isEmpty()) {
			warnings.add("ESC to cancel");
			String warningStr = join(warnings, " - ");
			getGroundPanel().drawNotificationBesideMouse(warningStr, graphics);
		}
	}

	private List<Position> getNewPositionsWhenOverLineElement(
			Position mousePosition, ProductionLineElement element) {

		if (isBuilding()) {
			Position lastPosition = computeLastPosition(element, mousePosition);
			return createPositionsTo(lastPosition);
		} else {
			EdgeType edgeType = getNearestAvailableConnectionEdgeType(element,
					mousePosition);
			Position firstPosition = edgeType == null ? mousePosition
					: getConnectionPositionByEdgeType(element, edgeType);
			return Arrays.asList(firstPosition);
		}
	}

	private List<Position> getNewPositionsWhenOverNonLineElement(
			Position mousePosition) {
		if (isBuilding())
			return createPositionsTo(mousePosition);
		else
			return Arrays.asList(mousePosition);
	}

	private void drawConveyorRectangle(Position position, Color color,
			Graphics2D graphics) {
		getPainter().drawRectangle(graphics, position, 1, 1, color);
	}

	private void drawAllConveyorPositions(Graphics2D graphics) {
		for (Position pos : this.conveyorPositions)
			drawConveyorRectangle(pos, OK_COLOR, graphics);
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
