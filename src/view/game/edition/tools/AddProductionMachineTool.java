package view.game.edition.tools;

import model.game.Game;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import view.game.GamePanel;

public class AddProductionMachineTool extends AddMachineTool {

	public AddProductionMachineTool(GamePanel gamePanel, Game game,
			MachineType machineType) {
		super(gamePanel, game, machineType);
	}

	@Override
	protected Machine createMachine() {
		return new ProductionMachine(getMachineType());
	}

}
