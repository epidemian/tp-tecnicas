package view.game.edition.tools;

import controller.game.GamePanelController;
import model.game.Player;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.QualityControlMachine;

public class AddQualityControlMachineTool extends AddMachineTool {

	public AddQualityControlMachineTool(GamePanelController gamePanelController
                , Player game, MachineType machineType) {
		super(gamePanelController, game, machineType);
	}
	
	@Override
	protected Machine createMachine() {
		return new QualityControlMachine(getMachineType());
	}

}
