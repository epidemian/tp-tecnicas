package controller.game.edition.tools;

import java.awt.Graphics2D;

import model.game.Player;
import model.warehouse.Position;
import model.warehouse.TileElement;
import view.game.GroundPanel;
import controller.game.GamePanelController;
import controller.game.edition.EditionTool;

public class SelectionTool extends EditionTool {

	private TileElement selectedTileElement = null;
	private GroundPanel groundPanel;

	public SelectionTool(GamePanelController gamePanelController, Player game) {
		super(gamePanelController, game);

		// Weeeeee!
		groundPanel = getGamePanel().getGroundPanelContainer().getGroundPanel();
	}

	@Override
	public void paint(Graphics2D graphics) {
		if (this.selectedTileElement != null)
			getGroundPanel().highlightTileElement(this.selectedTileElement,
					graphics);
	}

	@Override
	public void mouseClicked(Position position) {
		this.selectedTileElement = this.groundPanel.getGround()
				.getTileElementAt(position);
		System.out.println("Clicked on " + position + " and selected "
				+ selectedTileElement);

		this.getGamePanelController().setToolPanelFromSelectionTool(
				this.selectedTileElement);
	}

	@Override
	public void reset() {
		this.selectedTileElement = null;
	}
}
