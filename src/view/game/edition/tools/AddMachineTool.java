package view.game.edition.tools;

import static model.production.ProductionLineElement.connectLineElements;
import static model.utils.StringUtils.join;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.game.Game;
import model.production.Conveyor;
import model.production.Direction;
import model.production.Machine;
import model.production.MachineType;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.game.GamePanel;
import view.game.GroundPainter;
import view.game.GroundPanel;
import view.game.edition.EditionTool;

public abstract class AddMachineTool extends EditionTool {

	private static final Color OK_COLOR = new Color(0, 1, 0, 0.3F);
	private static final Color BAD_COLOR = new Color(1, 0, 0, 0.3F);
	private MachineType machineType;

	public AddMachineTool(GamePanel gamePanel, Game game,
			MachineType machineType) {
		super(gamePanel, game);
		this.machineType = machineType;
	}

	public MachineType getMachineType() {
		return machineType;
	}

	@Override
	public void reset() {
	}

	@Override
	public void paint(Graphics2D graphics) {
		Position mousePosition = this.getGroundPanel()
				.getCurrentMousePosition();
		if (mousePosition != null) {

			List<String> warnings = new ArrayList<String>();
			boolean canPutMachine = canPutMachineAt(mousePosition, warnings);
			boolean enoughMoney = haveEnoughMoney();
			Color color = canPutMachine && enoughMoney ? OK_COLOR : BAD_COLOR;

			if (!enoughMoney)
				warnings.add("Not enough money!");

			drawWarnings(graphics, warnings);
			drawMachineRectagle(graphics, mousePosition, color);
			drawInputArrow(graphics, mousePosition, color);
			drawOutputArrow(graphics, mousePosition, color);
		}
	}

	@Override
	public void mouseClicked(Position position) {
		if (canPutMachineAt(position) && haveEnoughMoney()) {
			Machine machine = buyAndAddMachine(position);
                        int balance = this.getGame().getBudget().getBalance();
                        this.getGamePanel().getBudgetPanel().setMoneyBalance(balance);

			tryConnectInput(machine);
			tryConnectOutput(machine);
		}
	}

	protected abstract Machine buyAndAddMachine(Position position);

	private void drawWarnings(Graphics2D graphics, List<String> warnings) {
		if (!warnings.isEmpty())
			getGroundPanel().drawNotificationBesideMouse(join(warnings, " - "),
					graphics);
	}

	private void drawMachineRectagle(Graphics2D graphics,
			Position mousePosition, Color color) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();
		getPainter().drawRectangle(graphics, mousePosition, width, height,
				color);
	}

	private void drawInputArrow(Graphics2D graphics, Position mousePosition,
			Color color) {
		Position inPos = mousePosition.add(this.machineType
				.getInputRelativePosition());
		Direction inDir = this.machineType.getInputConnectionDirection();
		getPainter().drawInputArrow(inPos, inDir, color, graphics);
	}

	private void drawOutputArrow(Graphics2D graphics, Position mousePosition,
			Color color) {
		Position outPos = mousePosition.add(this.machineType
				.getOutputRelativePosition());
		Direction outDir = this.machineType.getOutputConnectionDirection();
		getPainter().drawOutputArrow(outPos, outDir, color, graphics);
	}

	private void tryConnectInput(Machine machine) {
		Position inputPos = machine.getInputConnectionPosition();
		Direction inputDir = machine.getInputConnectionDirection();
		Conveyor conveyor = searchForInputConveyor(inputPos, inputDir);
		if (conveyor != null)
			connectLineElements(conveyor, machine);
	}

	private void tryConnectOutput(Machine machine) {
		Position outputPos = machine.getOutputConnectionPosition();
		Direction outputDir = machine.getOutputConnectionDirection();
		Conveyor conveyor = searchForOutputConveyor(outputPos, outputDir);
		if (conveyor != null)
			connectLineElements(conveyor, machine);
	}

	private boolean canPutMachineAt(Position position) {
		return canPutMachineAt(position, null);
	}

	private boolean canPutMachineAt(Position position, List<String> whyNot) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();

		boolean insideBounds = getGround().isAreaInsideBounds(width, height,
				position);
		if (!insideBounds && whyNot != null)
			whyNot.add("Out of bounds");

		boolean canPutMachine = getGround().canPutTileElementByDimension(width,
				height, position);
		if (!canPutMachine && insideBounds && whyNot != null)
			whyNot.add("Area not empty");

		return canPutMachine && canPutInput(position, whyNot)
				&& canPutOutput(position, whyNot);
	}

	private boolean canPutInput(Position position, List<String> whyNot) {
		Position inputPos = position.add(getMachineType()
				.getInputRelativePosition());
		boolean emptyArea = getGround().canPutTileElementByDimension(1, 1,
				inputPos);

		Direction inputDir = getMachineType().getInputConnectionDirection();
		boolean canConnect = canConnectInput(inputPos, inputDir);

		boolean canPutInput = emptyArea || canConnect;
		if (!canPutInput && whyNot != null)
			whyNot.add("Cannot connect input");

		return canPutInput;
	}

	private boolean canPutOutput(Position position, List<String> whyNot) {
		Position outputPos = position.add(getMachineType()
				.getOutputRelativePosition());
		boolean emptyArea = getGround().canPutTileElementByDimension(1, 1,
				outputPos);

		Direction outputDir = getMachineType().getOutputConnectionDirection();
		boolean canConnect = canConnectOutput(outputPos, outputDir);

		boolean canPutOutput = emptyArea || canConnect;
		if (!canPutOutput && whyNot != null)
			whyNot.add("Cannot connect output");

		return canPutOutput;
	}

	private boolean canConnectInput(Position inputPosition, Direction inputDir) {
		return searchForInputConveyor(inputPosition, inputDir) != null;
	}

	private boolean canConnectOutput(Position outputPosition,
			Direction outputDir) {
		return searchForOutputConveyor(outputPosition, outputDir) != null;
	}

	private Conveyor searchForInputConveyor(Position inputPosition,
			Direction inputDir) {
		TileElement element = getGround().getTileElementAt(inputPosition);
		Conveyor conveyor = recognizeConveyor(element);
		if (conveyor != null && !conveyor.hasNextLineElement()) {
			Position expectedConveyorOutputPos = inputPosition
					.subtract(inputDir.getAssociatedPosition());
			boolean canConnect = conveyor.getOutputConnectionPosition().equals(
					expectedConveyorOutputPos);
			if (!canConnect)
				conveyor = null;
		}
		return conveyor;
	}

	private Conveyor searchForOutputConveyor(Position outputPosition,
			Direction outputDir) {
		TileElement element = getGround().getTileElementAt(outputPosition);
		Conveyor conveyor = recognizeConveyor(element);
		if (conveyor != null && !conveyor.hasPreviousLineElement()) {
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
		return this.getGame().canPurchase(this.machineType.getPrice());
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

	private static Conveyor recognizeConveyor(TileElement element) {
		return ConveyorRecognizer.recognizeConveyor(element);
	}
}

class ConveyorRecognizer extends TileElementVisitor {

	private static final ConveyorRecognizer INSTANCE = new ConveyorRecognizer();
	private Conveyor conveyor;

	private ConveyorRecognizer() {
	}

	public static Conveyor recognizeConveyor(TileElement element) {
		INSTANCE.conveyor = null;
		element.accept(INSTANCE);
		return INSTANCE.conveyor;
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.conveyor = conveyor;
	}
}