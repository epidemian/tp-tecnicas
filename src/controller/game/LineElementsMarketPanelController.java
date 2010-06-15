package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import model.game.Game;
import model.production.MachineType;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;

import view.game.LineElementsMarketPanel;
import view.game.TileElementImageRecognizer;
import view.game.edition.EditionActions;

public class LineElementsMarketPanelController {

	private List<ProductionMachine> productionMachines;
	private List<QualityControlMachine> qualityControlMachines;

	private EditionActions editonActions;
	private LineElementsMarketPanel marketPanel;
	private int machineTab;

	public LineElementsMarketPanelController(Game game,
			LineElementsMarketPanel marketPanel, EditionActions editionActions) {

		this.productionMachines = game.getProductionMachines();
		this.qualityControlMachines = game.getQualityControlMachines();

		// TODO check not null.
		this.editonActions = editionActions;
		this.marketPanel = marketPanel;
		this.machineTab = 0;

		this.marketPanel
				.addNextMachinesButtonActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						LineElementsMarketPanelController.this
								.increaseMachineTab();
					}
				});

		this.marketPanel
				.addPreviousMachinesButtonActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						LineElementsMarketPanelController.this
								.decreseMachineTab();

					}
				});

		this.marketPanel.addConveyorButtonActionListener(this.editonActions
				.getActionToSetConveyorTool());

		// TODO edition tool para input.
		
		this.setUpMachineButtons();
	}

	private void increaseMachineTab() {
		int buttonsSize = this.marketPanel.getMachineButtonsSize();
		int machinesSize = this.productionMachines.size()
				+ this.qualityControlMachines.size();

		if (machinesSize / buttonsSize >= this.machineTab + 1)
			this.machineTab++;

		this.setUpMachineButtons();
	}

	private void decreseMachineTab() {
		if (this.machineTab - 1 >= 0) {
			this.machineTab--;
		}
		this.setUpMachineButtons();
	}

	/**
	 * Determines the buttons that has to be shown on the panel.
	 */
	private void setUpMachineButtons() {
		int buttonsSize = this.marketPanel.getMachineButtonsSize();
		int prodMachinesSize = this.productionMachines.size();
		int qualMachinesSize = this.qualityControlMachines.size();
		int machinesSize = prodMachinesSize + qualMachinesSize;

		int startIndex = buttonsSize * this.machineTab;
		int lastIndex = machinesSize >= startIndex + buttonsSize ? startIndex
				+ buttonsSize : machinesSize;

		// TODO terminar!
		int startIndexProdMachine = 0;
		int lastIndexProdMachine = 0;

		int startIndexQualMachine = 0;
		int lastIndexQualMachine = 0;

		int j = 0;
		for (int i = startIndexProdMachine; i < lastIndexProdMachine; i++, j++) {

			/*
			 * TODO encapsular comportamiento en un método aparte ya q
			 * probablemente haya codigo repetido para la quality control
			 * machine.
			 */
			MachineType mtype = this.productionMachines.get(i).getMachineType();
			this.marketPanel.setMachineButtonActionListener(j,
					this.editonActions.getActionToSetNewMachineTool(mtype));

			// TODO escalar imagen.
			BufferedImage image = TileElementImageRecognizer
					.getMachineImage(mtype);
			this.marketPanel.setMachineButtonVisible(j, true);
			this.marketPanel.setMachineButtonIcon(j, new ImageIcon(image));
		}

		for (int i = startIndexQualMachine; i < lastIndexQualMachine; i++, j++) {
			// TODO ver edition tools para quality control machine.
		}

		for (; j < buttonsSize; j++) {
			this.marketPanel.setMachineButtonVisible(j, false);
		}
	}
}
