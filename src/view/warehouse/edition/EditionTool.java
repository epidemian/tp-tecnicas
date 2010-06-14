package view.warehouse.edition;

import model.game.Game;
import model.warehouse.Position;
import view.Painter;
import view.warehouse.GamePanel;

public abstract class EditionTool implements Painter {

	private GamePanel gamePanel;
	private Game game;

	public EditionTool(GamePanel gamePanel, Game game) {
		this.gamePanel = gamePanel;
		this.game = game;
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public Game getGame() {
		return game;
	}

	public abstract void cancelOperation();

	public abstract void mouseMoved(Position position);

	public abstract void mouseClicked(Position position);

}
