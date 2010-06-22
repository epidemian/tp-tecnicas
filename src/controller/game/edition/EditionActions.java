package controller.game.edition;

import static java.lang.System.out;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.game.Player;
import model.production.elements.machine.MachineType;
import controller.game.GamePanelController;
import controller.game.edition.tools.AddConveyorTool;
import controller.game.edition.tools.AddInputLineElementTool;
import controller.game.edition.tools.AddProductionMachineTool;
import controller.game.edition.tools.AddQualityControlMachineTool;
import controller.game.edition.tools.DeleteTool;
import controller.game.edition.tools.MoveTool;
import controller.game.edition.tools.SelectionTool;

public class EditionActions {

	private GamePanelController gamePanelController;
	private Player game;

	public EditionActions(GamePanelController gamePanelController, Player game) {

		this.gamePanelController = gamePanelController;
		this.game = game;
	}

	public Action getEscAction() {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanelController.getGroundPanelController().getEditionTool()
						.reset();
			}

			private static final long serialVersionUID = 1L;
		};
	}

	public Action getActionToSetNewProductionMachineTool(
			final MachineType machineType) {
		EditionTool tool = new AddProductionMachineTool(gamePanelController,
				game, machineType);
		return getActionToSetTool(tool, "Add-Machine");
	}

	public Action getActionToSetNewQualityControlMachineTool(
			final MachineType machineType) {
		EditionTool tool = new AddQualityControlMachineTool(
				gamePanelController, game, machineType);
		return getActionToSetTool(tool, "Add-Quality-Control-Machine");
	}

	public Action getActionToSetSelectionTool() {
		EditionTool tool = new SelectionTool(gamePanelController, game);
		return getActionToSetTool(tool, "Selection");
	}

	public Action getActionToSetConveyorTool() {
		EditionTool tool = new AddConveyorTool(gamePanelController, game);
		return getActionToSetTool(tool, "Add-Conveyor");
	}

	public Action getActionToSetInputLineElementTool() {
		EditionTool tool = new AddInputLineElementTool(gamePanelController,
				game);
		return getActionToSetTool(tool, "Add-Conveyor");
	}

	public Action getActionToSetDeleteTool() {
		EditionTool tool = new DeleteTool(gamePanelController, game);
		return getActionToSetTool(tool, "Delete");
	}

	public Action getActionToSetMoveTool() {
		EditionTool tool = new MoveTool(gamePanelController, game);
		return getActionToSetTool(tool, "Move");
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
				gamePanelController.getGroundPanelController().setEditionTool(
						tool);
			}

			private static final long serialVersionUID = 1L;
		};
	}

}
