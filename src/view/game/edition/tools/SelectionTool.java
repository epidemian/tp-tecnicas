package view.game.edition.tools;

import controller.game.GamePanelController;
import controller.game.InputSelectionPanelController;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.game.Player;
import model.production.elements.InputProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.game.GamePanel;
import view.game.GroundPanel;
import view.game.InputSelectionPanel;
import view.game.MachineSelectionPanel;
import view.game.TileElementImageRecognizer;
import view.game.edition.EditionTool;

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
			this.groundPanel.getPainter().highlightTileElement(this.selectedTileElement, graphics);
	}

	@Override
	public void mouseClicked(Position position) {
            this.selectedTileElement = this.groundPanel.getGround()
                            .getTileElementAt(position);
            System.out.println("Clicked on " + position + " and selected "
                            + selectedTileElement);

            this.getGamePanelController().setToolPanelFromSelectionTool(this.selectedTileElement);
	}

	@Override
	public void reset() {
		this.selectedTileElement = null;
	}
}
