package controller.game.edition.tools;

import model.game.Player;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.QualityControlMachine;
import controller.game.GamePanelController;

public class AddQualityControlMachineTool extends AddMachineTool {

	public AddQualityControlMachineTool(
			GamePanelController gamePanelController, Player game,
			MachineType machineType) {
		super(gamePanelController, game, machineType);
	}

	@Override
	protected ProductionLineElement createLineElement() {
		return new QualityControlMachine(getMachineType(),getPlayer().getConfig());
	}

}
