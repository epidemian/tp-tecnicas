package view.game.edition.tools;

import java.awt.Color;
import java.awt.Graphics2D;

import model.game.Game;
import model.warehouse.EmptyTileElement;
import model.warehouse.Position;
import model.warehouse.TileElement;
import view.game.GamePanel;
import view.game.GroundPanel;
import view.game.edition.EditionTool;

public class SelectionTool extends EditionTool {
	
	private TileElement selectedTileElement = null;
	private GroundPanel groundPanel;

	public SelectionTool(GamePanel gamePanel, Game game) {
		super(gamePanel, game);

		// Weeeeee!
		groundPanel = getGamePanel().getGroundPanelContainer().getGroundPanel();
	}

	@Override
	public void cancelOperation() {
	}

	@Override
	public void paint(Graphics2D graphics) {
		if (this.selectedTileElement != null)
			this.groundPanel.getPainter().highlightTileElement(this.selectedTileElement, graphics);
	}

	@Override
	public void mouseClicked(Position position) {
		this.selectedTileElement = this.groundPanel.getGround()
				.getTileElementAt(position);
		System.out.println("Clicked on " + position + " and selected "
				+ selectedTileElement);

		// TODO: something like:
		// this.getGamePanel().getInformationPanel().showTileElementInformation(this.selectedTileElement);
	}

}
