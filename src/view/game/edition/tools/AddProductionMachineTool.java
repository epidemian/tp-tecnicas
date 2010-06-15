package view.game.edition.tools;

import model.game.Game;
import model.production.Machine;
import model.production.MachineType;
import model.warehouse.Position;
import view.game.GamePanel;

public class AddProductionMachineTool extends AddMachineTool {

	public AddProductionMachineTool(GamePanel gamePanel, Game game,
			MachineType machineType) {
		super(gamePanel, game, machineType);
	}

	@Override
	protected Machine buyAndAddMachine(Position position) {
		return getGame().buyAndAddProductionMachine(getMachineType(), position);
	}

}
