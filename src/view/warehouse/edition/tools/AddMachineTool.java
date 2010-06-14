package view.warehouse.edition.tools;

import java.awt.Color;
import java.awt.Graphics;

import view.warehouse.GamePanel;
import view.warehouse.GroundPanel;
import view.warehouse.edition.EditionTool;
import model.game.Budget;
import model.game.Game;
import model.production.Machine;
import model.production.MachineType;
import model.production.ProductionMachine;
import model.warehouse.Position;

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
	public void paint(Graphics graphics) {
		Position position = this.groundPanel.getCurrentMousePosition();
		if (position != null) {
			Color color = canPutMachineAt(position) ? OK_COLOR : BAD_COLOR;
			this.groundPanel.getPainter().paintResctangle(graphics, position,
					this.machineType.getWidth(), this.machineType.getHeight(),
					color);
		}
	}

	@Override
	public void mouseClicked(Position position) {
		if (canPutMachineAt(position)) {
			boolean enoughMoney = this.getGame().canPurchase(
					this.machineType.getPrice());
			if (!enoughMoney) {
				// TODO: show noy enough money notification.
			}
			else {
				this.getGame().buyAndAddProductionMachine(this.machineType, position);
				// TODO: habría que avisar a la vista cosa que se actualice...
				// this.getGamePanel().getTopPanel().setMoneyBalance(budget.getBalance());
			}

		}
	}

	private boolean canPutMachineAt(Position position) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();
		return this.getGame().getGround().canPutTileElementByDimension(width,
				height, position);
	}

}
