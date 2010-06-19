package view.game.edition.tools;

import model.game.Player;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.QualityControlMachine;
import view.game.GamePanel;

public class AddQualityControlMachineTool extends AddMachineTool {

	public AddQualityControlMachineTool(GamePanel gamePanel, Player game,
			MachineType machineType) {
		super(gamePanel, game, machineType);
	}
	
	@Override
	protected Machine createMachine() {
		return new QualityControlMachine(getMachineType());
	}

}
