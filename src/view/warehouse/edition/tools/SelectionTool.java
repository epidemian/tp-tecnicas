package view.warehouse.edition.tools;

import java.awt.Color;
import java.awt.Graphics;

import model.game.Game;
import model.warehouse.EmptyTileElement;
import model.warehouse.Position;
import model.warehouse.TileElement;
import view.warehouse.GamePanel;
import view.warehouse.GroundPanel;
import view.warehouse.edition.EditionTool;

public class SelectionTool extends EditionTool {

	private static final Color SELECTION_COLOR = new Color(0, 1, 0, 0.3F);
	
	private TileElement selectedTileElement = EmptyTileElement.getInstance();
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
	public void paint(Graphics graphics) {
		if (!this.selectedTileElement.equals(EmptyTileElement.getInstance()))
			this.groundPanel.getPainter().paintResctangle(graphics,
					selectedTileElement.getPosition(),
					selectedTileElement.getWidth(),
					selectedTileElement.getHeight(), SELECTION_COLOR);
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
