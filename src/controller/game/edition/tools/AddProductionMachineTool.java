package controller.game.edition.tools;

import model.game.Player;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import controller.game.GamePanelController;

public class AddProductionMachineTool extends AddMachineTool {

	public AddProductionMachineTool(GamePanelController gamePanelController
                , Player game, MachineType machineType) {
		super(gamePanelController, game, machineType);
	}

	@Override
	protected Machine createMachine() {
		return new ProductionMachine(getMachineType());
	}

}
