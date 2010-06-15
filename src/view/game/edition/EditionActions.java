package view.game.edition;

import static java.lang.System.out;
import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.game.Game;
import model.production.MachineType;
import view.game.GamePanel;
import view.game.GroundPanel;
import view.game.edition.tools.AddConveyorTool;
import view.game.edition.tools.AddMachineTool;
import view.game.edition.tools.AddProductionMachineTool;
import view.game.edition.tools.AddQualityControlMachineTool;
import view.game.edition.tools.SelectionTool;

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
				groundPanel.getEditionTool().reset();
			}

			private static final long serialVersionUID = 1L;
		};
	}

	public Action getActionToSetNewProductionMachineTool(
			final MachineType machineType) {
		EditionTool tool = new AddProductionMachineTool(gamePanel, game,
				machineType);
		return getActionToSetTool(tool, "Add-Machine");
	}

	public Action getActionToSetNewQualityControlMachineTool(
			final MachineType machineType) {
		EditionTool tool = new AddQualityControlMachineTool(gamePanel, game,
				machineType);
		return getActionToSetTool(tool, "Add-Quality-Control-Machine");
	}

	public Action getActionToSetSelectionTool() {
		EditionTool tool = new SelectionTool(gamePanel, game);
		return getActionToSetTool(tool, "Selection");
	}

	public Action getActionToSetConveyorTool() {
		EditionTool tool = new AddConveyorTool(gamePanel, game);
		return getActionToSetTool(tool, "Add-Conveyor");
	}
	
	public Action getActionToSetRawMaterialInputTool() {
		EditionTool tool = new AddConveyorTool(gamePanel, game);
		return getActionToSetTool(tool, "Add-Conveyor");
	}

	private static void printToolSelection(String toolName) {
		out.println("You have selected the " + toolName + " Tool");
	}

	private Action getActionToSetTool(final EditionTool tool,
			final String toolName) {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printToolSelection(toolName);
				groundPanel.setEditionTool(tool);
			}

			private static final long serialVersionUID = 1L;
		};
	}
}
