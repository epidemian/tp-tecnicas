package view.warehouse.edition.tools;

import java.awt.Color;
import java.awt.Graphics;

import view.warehouse.GamePanel;
import view.warehouse.GroundPanel;
import view.warehouse.edition.EditionTool;
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
		Color color = canPutMachineAt(position) ? Color.GREEN : Color.RED;
		this.groundPanel.getPainter().paintResctangle(graphics, position,
				this.machineType.getWidth(), this.machineType.getHeight(),
				color);
	}

	@Override
	public void mouseClicked(Position position) {
		if (canPutMachineAt(position)) {
			ProductionMachine machine = new ProductionMachine(this.machineType);

			// TODO: Esto no se si irá así jajjaa
			this.getGame().getBudget().decrement(this.machineType.getPrice());
			this.getGame().getGround().addTileElement(machine, position);
		}
	}

	private boolean canPutMachineAt(Position position) {
		int width = this.machineType.getWidth();
		int height = this.machineType.getHeight();
		boolean areaEmpty = this.groundPanel.getGround().isAreaEmpty(position,
				width, height);
		return areaEmpty;
	}

}
