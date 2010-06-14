package view.warehouse.edition.tools;

import java.awt.Color;
import java.awt.Graphics2D;

import model.game.Game;
import model.production.Direction;
import model.production.MachineType;
import model.warehouse.Ground;
import model.warehouse.Position;
import view.warehouse.GamePanel;
import view.warehouse.GroundPainter;
import view.warehouse.GroundPanel;
import view.warehouse.edition.EditionTool;

public class AddMachineTool extends EditionTool {

	private static final Color OK_COLOR = new Color(0, 1, 0, 0.3F);
	private static final Color BAD_COLOR = new Color(1, 0, 0, 0.3F);
	private MachineType machineType;
	private GroundPanel groundPanel;

	public AddMachineTool(GamePanel gamePanel, Game game,
			MachineType machineType) {
		super(gamePanel, game);
		this.machineType = machineType;
		groundPanel = getGamePanel().getGroundPanelContainer().getGroundPanel();
	}

	@Override
	public void cancelOperation() {
	}

	@Override
	public void paint(Graphics2D graphics) {
		Position position = this.groundPanel.getCurrentMousePosition();
		if (position != null) {

			Color color = canPutMachineAt(position) ? OK_COLOR : BAD_COLOR;
			GroundPainter painter = this.groundPanel.getPainter();
			int width = this.machineType.getWidth();
			int height = this.machineType.getHeight();
			painter.drawRectangle(graphics, position, width, height, color);

			Position inPos = position.add(this.machineType
					.getInputRelativePosition());
			Direction inDir = this.machineType.getInputConnectionDirection();
			painter.drawInputArrow(inPos, inDir, color, graphics);

			Position outPos = position.add(this.machineType
					.getOutputRelativePosition());
			Direction outDir = this.machineType.getOutputConnectionDirection();
			painter.drawOutputArrow(outPos, outDir, color, graphics);
		}
	}

	@Override
	public void mouseClicked(Position position) {
		if (canPutMachineAt(position)) {
			boolean enoughMoney = this.getGame().canPurchase(
					this.machineType.getPrice());
			if (!enoughMoney) {
				// TODO: show noy enough money notification.
			} else {
				this.getGame().buyAndAddProductionMachine(this.machineType,
						position);
				// TODO: habría que avisar a la vista cosa que se actualice...
				// this.getGamePanel().getTopPanel().setMoneyBalance(budget.getBalance());
			}

		}
	}

	private boolean canPutMachineAt(Position position) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();
		Ground ground = this.getGame().getGround();
		Position inPos = position.add(this.machineType
				.getInputRelativePosition());
		Position outPos = position.add(this.machineType
				.getOutputRelativePosition());

		boolean canPutMachine = ground.canPutTileElementByDimension(width,
				height, position);
		boolean canPutInput = ground.canPutTileElementByDimension(1, 1, inPos);
		boolean canPutOutput = ground
				.canPutTileElementByDimension(1, 1, outPos);
		return canPutMachine && canPutInput && canPutOutput;
	}
}
