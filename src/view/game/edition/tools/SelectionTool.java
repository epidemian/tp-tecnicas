package view.game.edition.tools;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.game.Player;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.game.GamePanel;
import view.game.GroundPanel;
import view.game.MachineSelectionPanel;
import view.game.TileElementImageRecognizer;
import view.game.edition.EditionTool;

public class SelectionTool extends EditionTool {
	
	private TileElement selectedTileElement = null;
	private GroundPanel groundPanel;

	public SelectionTool(GamePanel gamePanel, Player game) {
		super(gamePanel, game);

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

                this.selectedTileElement.accept(new TileElementVisitor() {

                    // TODO hacer un controller para esto!
                    private MachineSelectionPanel visitMachine(Machine machine){
                        MachineSelectionPanel machinePanel = new MachineSelectionPanel();

                        int price = machine.getSalePrice();
                        BufferedImage image = TileElementImageRecognizer.getMachineImage(machine.getMachineType());
                        String machineType = machine.getMachineType().getName();
                        double fail = machine.getFailProductProcessChance();
                        String state = machine.getMachineState().toString();

                        machinePanel.setMachinePrice(price);
                        machinePanel.setMachineImage(image);
                        machinePanel.setMachineType(machineType);
                        machinePanel.setFailProductProcessChance(fail);
                        machinePanel.setMachineState(state);

                        return machinePanel;
                    }

                    @Override
                    public void visitProductionMachine(ProductionMachine machine) {
                        // Creates machine panel.
                        MachineSelectionPanel machinePanel = this.visitMachine(machine);
                        machinePanel.setProductionMachineLabels();
                        // Sets panel.
                        getGamePanel().setToolPanel(machinePanel);
                    }

                    @Override
                    public void visitQualityControlMachine(QualityControlMachine machine) {
                        // Creates machine panel.
                        MachineSelectionPanel machinePanel = this.visitMachine(machine);
                        machinePanel.setQualityControlMachineLabels();
                        // Sets panel.
                        getGamePanel().setToolPanel(machinePanel);
                    }
                });
	}

	@Override
	public void reset() {
		this.selectedTileElement = null;
	}
}
