package controller.game.edition.tools;

import java.awt.Graphics2D;

import model.game.Player;
import model.warehouse.Position;
import model.warehouse.TileElement;
import controller.game.GamePanelController;
import controller.game.edition.EditionTool;

public class SelectionTool extends EditionTool {

	private TileElement selectedTileElement = null;

	public SelectionTool(GamePanelController gamePanelController, Player game) {
		super(gamePanelController, game);
	}

	@Override
	public void paint(Graphics2D graphics) {
		if (this.selectedTileElement != null)
			getGroundPanel().highlightTileElement(this.selectedTileElement,
					graphics);
	}

	@Override
	public void mouseClicked(Position position) {
		this.selectedTileElement = this.getGroundPanel().getGround()
				.getTileElementAt(position);
		
		this.getGamePanelController().setToolPanelFromSelectionTool(
				this.selectedTileElement);
	}

	@Override
	public void reset() {
		this.selectedTileElement = null;
	}
}
