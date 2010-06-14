package view.warehouse.edition;

import static java.lang.System.out;
import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.game.Game;
import model.production.MachineType;
import view.warehouse.GamePanel;
import view.warehouse.GroundPanel;
import view.warehouse.edition.tools.AddMachineTool;
import view.warehouse.edition.tools.SelectionTool;

public class EditionActions {

	private GamePanel gamePanel;
	private Game game;
	private GroundPanel groundPanel;

	public EditionActions(GamePanel gamePanel, Game game) {
		checkNotNull(gamePanel, "game panel");
		checkNotNull(game, "game");
		this.gamePanel = gamePanel;
		this.game = game;
		this.groundPanel = gamePanel.getGroundPanelContainer().getGroundPanel();
	}

	public Action getEscAction() {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				groundPanel.getEditionTool().cancelOperation();
			}

			private static final long serialVersionUID = 1L;
		};
	}

	public Action getActionToSetNewMachineTool(final MachineType machineType) {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printToolSelection("Add-Machine");
				groundPanel.setEditionTool(new AddMachineTool(gamePanel, game, machineType));
			}

			private static final long serialVersionUID = 1L;
		};
	}

	public Action getActionToSetSelectionTool() {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printToolSelection("Selection");
				groundPanel.setEditionTool(new SelectionTool(gamePanel, game));
			}

			private static final long serialVersionUID = 1L;
		};
	}
	
	private static void printToolSelection(String toolName) {
		out.println("You have selected the " + toolName + " Tool");
	}
	
	
}
