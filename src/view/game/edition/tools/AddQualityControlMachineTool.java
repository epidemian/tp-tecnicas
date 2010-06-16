package view.game.edition.tools;

import model.game.Game;
import model.production.Machine;
import model.production.MachineType;
import model.production.QualityControlMachine;
import view.game.GamePanel;

public class AddQualityControlMachineTool extends AddMachineTool {

	public AddQualityControlMachineTool(GamePanel gamePanel, Game game,
			MachineType machineType) {
		super(gamePanel, game, machineType);
	}
	
	@Override
	protected Machine createMachine() {
		return new QualityControlMachine(getMachineType());
	}

}
