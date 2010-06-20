package controller.game.edition.tools;

import controller.game.GamePanelController;
import controller.game.edition.EditionTool;
import static controller.game.edition.tools.Colors.*;
import static model.production.elements.ProductionLineElement.connectLineElements;
import static model.utils.StringUtils.join;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.game.Player;
import model.production.Direction;
import model.production.RawMaterialType;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.warehouse.Position;
import model.warehouse.TileElement;

/*
 * TODO: Hay un montón de código compiado de AddMachineTool.... generalizar...
 */
public class AddInputLineElementTool extends EditionTool {

	private static final Direction OUTPUT_DIR = Direction.EAST;

	public AddInputLineElementTool(GamePanelController gamePanelController, Player game) {
		super(gamePanelController, game);
	}

	@Override
	public void reset() {
	}

	@Override
	public void mouseClicked(Position position) {
		if (canPutElementAt(position) && haveEnoughMoney()) {
			InputProductionLineElement inputElement = new InputProductionLineElement(
					OUTPUT_DIR);
			getGame().buyAndAddProductionLineElement(inputElement, position);
			updateBudgetView();

			tryConnectOutput(inputElement);
		}
	}

	private void updateBudgetView() {
		int balance = this.getGame().getBudget().getBalance();
		this.getGamePanel().getBudgetPanel().setMoneyBalance(balance);
	}

	@Override
	public void paint(Graphics2D graphics) {
		Position mousePosition = this.getGroundPanel()
				.getCurrentMousePosition();
		if (mousePosition != null) {

			List<String> warnings = new ArrayList<String>();
			boolean canPutMachine = canPutElementAt(mousePosition, warnings);
			boolean enoughMoney = haveEnoughMoney();
			Color color = canPutMachine && enoughMoney ? OK_COLOR : BAD_COLOR;

			if (!enoughMoney)
				warnings.add("Not enough money!");

			drawWarnings(graphics, warnings);
			drawElementRectagle(graphics, mousePosition, color);
			drawOutputArrow(graphics, mousePosition, color);
		}
	}

	private void drawWarnings(Graphics2D graphics, List<String> warnings) {
		if (!warnings.isEmpty())
			getGroundPanel().drawNotificationBesideMouse(join(warnings, " - "),
					graphics);
	}

	private void drawElementRectagle(Graphics2D graphics,
			Position mousePosition, Color color) {
		getPainter().drawRectangle(graphics, mousePosition, 1, 1, color);
	}

	private void drawOutputArrow(Graphics2D graphics, Position mousePosition,
			Color color) {
		Position outPos = mousePosition.add(OUTPUT_DIR.getAssociatedPosition());
		getPainter().drawOutputArrow(outPos, OUTPUT_DIR, color, graphics);
	}

	private void tryConnectOutput(InputProductionLineElement inputElement) {
		Position outputPos = inputElement.getOutputConnectionPosition();
		Direction outputDir = inputElement.getOutputConnectionDirection();
		Conveyor conveyor = searchForOutputConveyor(outputPos, outputDir);
		if (conveyor != null)
			connectLineElements(conveyor, inputElement);
	}

	private boolean canPutElementAt(Position position) {
		return canPutElementAt(position, null);
	}

	private boolean canPutElementAt(Position position, List<String> whyNot) {
		boolean insideBounds = getGround().isAreaInsideBounds(1, 1, position);
		if (!insideBounds && whyNot != null)
			whyNot.add("Out of bounds");

		boolean canPutElement = getGround().canAddTileElementByDimension(1, 1,
				position);
		if (!canPutElement && insideBounds && whyNot != null)
			whyNot.add("Area not empty");

		return canPutElement && canPutOutput(position, whyNot);
	}

	private boolean canPutOutput(Position position, List<String> whyNot) {
		Position outputPos = position.add(OUTPUT_DIR.getAssociatedPosition());
		boolean emptyArea = getGround().canAddTileElementByDimension(1, 1,
				outputPos);

		boolean canConnect = canConnectOutput(outputPos, OUTPUT_DIR);

		boolean canPutOutput = emptyArea || canConnect;
		if (!canPutOutput && whyNot != null)
			whyNot.add("Cannot connect output");

		return canPutOutput;
	}

	private boolean canConnectOutput(Position outputPosition,
			Direction outputDir) {
		return searchForOutputConveyor(outputPosition, outputDir) != null;
	}

	private Conveyor searchForOutputConveyor(Position outputPosition,
			Direction outputDir) {
		TileElement element = getGround().getTileElementAt(outputPosition);
		Conveyor conveyor = recognizeConveyor(element);
		if (conveyor == null || conveyor.hasPreviousLineElement()) {
			conveyor = null;
		} else {
			Position expectedConveyorInputPos = outputPosition
					.subtract(outputDir.getAssociatedPosition());
			boolean canConnect = conveyor.getInputConnectionPosition().equals(
					expectedConveyorInputPos);
			if (!canConnect)
				conveyor = null;
		}
		return conveyor;
	}

	private boolean haveEnoughMoney() {
		return this.getGame().canAfford(
				InputProductionLineElement.PURCHASE_PRICE);
	}

	private static Conveyor recognizeConveyor(TileElement element) {
		return ConveyorRecognizer.recognizeConveyor(element);
	}

}
