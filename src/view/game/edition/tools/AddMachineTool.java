package view.game.edition.tools;

import static model.utils.StringUtils.join;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.game.Game;
import model.production.Direction;
import model.production.Machine;
import model.production.MachineType;
import model.warehouse.Ground;
import model.warehouse.Position;
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
	public void cancelOperation() {
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
			putMachineAt(position);
                        int balance = this.getGame().getBudget().getBalance();
                        this.getGamePanel().getBudgetPanel().setMoneyBalance(balance);
		}
	}

	protected abstract Machine putMachineAt(Position position);

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

	private GroundPainter getPainter() {
		GroundPainter painter = this.getGroundPanel().getPainter();
		return painter;
	}

	private boolean haveEnoughMoney() {
		return this.getGame().canPurchase(this.machineType.getPrice());
	}

	private boolean canPutMachineAt(Position position) {
		return canPutMachineAt(position, null);
	}

	private boolean canPutMachineAt(Position position, List<String> whyNot) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();

		boolean insideBounds = getGround().isAreaInsideBounds(width, height, position);
		if (!insideBounds && whyNot != null)
			whyNot.add("Out of bounds");

		boolean canPutMachine = getGround().canPutTileElementByDimension(width, height,
				position);
		if (!canPutMachine && insideBounds && whyNot != null)
			whyNot.add("Area not empty");

		boolean canPutInput = true;
		
		
		boolean canPutOutput = true;
		return canPutMachine && canPutInput && canPutOutput ;
	}

	private boolean canPutInput(Position position, List<String> whyNot) {
		Position inputPosition = position.add(getMachineType().getInputRelativePosition());
		boolean canPutInput = getGround().canPutTileElementByDimension(1, 1, inputPosition);
		return canPutInput;
	}

	private Ground getGround() {
		return this.getGame().getGround();
	}

	private GroundPanel getGroundPanel() {
		return this.getGamePanel().getGroundPanelContainer().getGroundPanel();
	}
}
