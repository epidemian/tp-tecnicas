package controller.game.edition.tools;

import model.game.Player;
import model.production.elements.machine.MachineType;
import controller.game.GamePanelController;

public abstract class AddMachineTool extends AbstractAddLineElementTool {

	private MachineType machineType;

	public AddMachineTool(GamePanelController gamePanelController, Player game,
			MachineType machineType) {
		super(gamePanelController, game);
		this.machineType = machineType;
	}

	protected MachineType getMachineType() {
		return machineType;
	}

}
