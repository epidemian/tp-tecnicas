package controller.game.edition;

import model.game.Player;
import model.warehouse.Ground;
import model.warehouse.Position;
import view.Painter;
import view.game.GamePanel;
import view.game.ground.StaticGroundPanel;
import controller.game.GamePanelController;

public abstract class EditionTool implements Painter {

	private Player game;
	private GamePanelController gamePanelController;

	public EditionTool(GamePanelController gamePanelController, Player game) {
		this.gamePanelController = gamePanelController;
		this.game = game;
	}

	protected GamePanel getGamePanel() {
		return gamePanelController.getGamePanel();
	}

	protected GamePanelController getGamePanelController() {
		return this.gamePanelController;
	}

	protected Player getGame() {
		return game;
	}

	protected Ground getGround() {
		return this.getGame().getGround();
	}

	protected StaticGroundPanel getGroundPanel() {
		return this.getGamePanel().getGroundPanel();
	}

	protected boolean isTileEmpty(Position position) {
		return this.getGround().canAddTileElementByDimension(1, 1, position);
	}

	public abstract void mouseClicked(Position mousePosition);

	public abstract void reset();
}
