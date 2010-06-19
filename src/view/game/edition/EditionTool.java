package view.game.edition;

import model.game.Player;
import model.warehouse.Ground;
import model.warehouse.Position;
import view.Painter;
import view.game.GamePanel;
import view.game.GroundPainter;
import view.game.GroundPanel;

public abstract class EditionTool implements Painter {

	private GamePanel gamePanel;
	private Player game;

	public EditionTool(GamePanel gamePanel, Player game) {
		this.gamePanel = gamePanel;
		this.game = game;
	}

	protected GamePanel getGamePanel() {
		return gamePanel;
	}

	protected Player getGame() {
		return game;
	}
	
	protected GroundPainter getPainter() {
		GroundPainter painter = this.getGroundPanel().getPainter();
		return painter;
	}

	protected Ground getGround() {
		return this.getGame().getGround();
	}

	protected GroundPanel getGroundPanel() {
		return this.getGamePanel().getGroundPanelContainer().getGroundPanel();
	}

	protected boolean isTileEmpty(Position position) {
		return this.getGround().canAddTileElementByDimension(1, 1, position);
	}
	
	public abstract void mouseClicked(Position mousePosition);

	public abstract void reset();
}
