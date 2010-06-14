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
			Color color = canPutMachineAt(position) ? Color.GREEN : Color.RED;
			this.groundPanel.getPainter().paintResctangle(graphics, position,
					this.machineType.getWidth(), this.machineType.getHeight(),
					color);
		}
	}

	@Override
	public void mouseClicked(Position position) {
		if (canPutMachineAt(position)) {
			ProductionMachine machine = new ProductionMachine(this.machineType);

			// TODO: Esto no se si irá así jajjaa
			Budget budget = this.getGame().getBudget();
			budget.decrement(this.machineType.getPrice());
			// TODO: habría que avisar a la vista cosa que se actualice...
			// this.getGamePanel().getTopPanel().setMoneyBalance(budget.getBalance());
			
			this.getGame().getGround().putTileElement(machine, position);
		}
	}

	private boolean canPutMachineAt(Position position) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();
		return this.groundPanel.getGround().canPutTileElementByDimension(width,
				height, position);
	}

}
