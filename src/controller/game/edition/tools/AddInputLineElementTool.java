package controller.game.edition.tools;

import model.game.Player;
import model.production.Direction;
import model.production.elements.InputProductionLineElement;
import model.production.elements.ProductionLineElement;
import controller.game.GamePanelController;

public class AddInputLineElementTool extends AbstractAddLineElementTool {

	private static final Direction OUTPUT_DIR = Direction.EAST;

	public AddInputLineElementTool(GamePanelController gamePanelController,
			Player game) {
		super(gamePanelController, game);
	}

	@Override
	protected ProductionLineElement createLineElement() {
		return new InputProductionLineElement(OUTPUT_DIR,getPlayer().getConfig());
	}

}
