package view.game.edition;

import model.game.Game;
import model.warehouse.Ground;
import model.warehouse.Position;
import view.Painter;
import view.game.GamePanel;
import view.game.GroundPainter;
import view.game.GroundPanel;

public abstract class EditionTool implements Painter {

	private GamePanel gamePanel;
	private Game game;

	public EditionTool(GamePanel gamePanel, Game game) {
		this.gamePanel = gamePanel;
		this.game = game;
	}

	protected GamePanel getGamePanel() {
		return gamePanel;
	}

	protected Game getGame() {
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

	public abstract void mouseClicked(Position mousePosition);

	public abstract void reset();
}
