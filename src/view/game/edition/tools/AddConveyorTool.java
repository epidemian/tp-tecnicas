package view.game.edition.tools;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.game.Game;
import model.production.Conveyor;
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
	EdgeType from;

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

		} else if (isTileEmpty(mousePosition)) {
			
		}
	}

	private void clickedOverNonLineElement(Position mousePosition) {
		if (isBuilding()) {
			List<Position> newPositions = createPositionsTo(mousePosition);
			if (areAllTilesEmpty(newPositions))
				this.conveyorPositions.addAll(newPositions);
		}
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
		this.from = null;
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
