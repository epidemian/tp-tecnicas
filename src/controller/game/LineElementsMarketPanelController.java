package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;

import model.game.Game;
import static model.utils.ArgumentUtils.*;
import model.production.MachineType;

import view.game.LineElementsMarketPanel;
import view.game.TileElementImageRecognizer;
import view.game.edition.EditionActions;

public class LineElementsMarketPanelController {

	private List<Action> machineButtonsActions;
	private List<BufferedImage> machineButtonsImages;

	private EditionActions editonActions;
	private LineElementsMarketPanel marketPanel;
	private int machineTab;

	public LineElementsMarketPanelController(Game game,
			LineElementsMarketPanel marketPanel, EditionActions editionActions) {

		checkNotNull(this.editonActions, "editionActions");
		checkNotNull(this.marketPanel, "marketPanel");

		this.machineButtonsActions = new ArrayList<Action>();
		this.machineButtonsImages = new ArrayList<BufferedImage>();

		this.machineTab = 0;

		initNextMachinesButtonActionListener();
		initPreviousMachinesButtonActionListener();

		// Conveyor action.
		this.marketPanel.addConveyorButtonActionListener(this.editonActions
				.getActionToSetConveyorTool());

		// Raw materials input action.
		this.marketPanel.addInputButtonActionListener(this.editonActions
				.getActionToSetRawMaterialInputTool());

		initMachineButtonsActionsAndImages(game);

		this.setUpButtons();
	}

	private void initMachineButtonsActionsAndImages(Game game) {
		// Production machine buttons actions n' images.
		for (MachineType mtype : game.getProductionMachinesTypes()) {
			this.machineButtonsImages.add(TileElementImageRecognizer
					.getMachineImage(mtype));
			this.machineButtonsActions.add(this.editonActions
					.getActionToSetNewProductionMachineTool(mtype));
		}

		// Quality control machine buttons actions n' images.
		for (MachineType mtype : game.getQualityControlMachinesTypes()) {
			this.machineButtonsImages.add(TileElementImageRecognizer
					.getMachineImage(mtype));
			this.machineButtonsActions.add(this.editonActions
					.getActionToSetNewQualityControlMachineTool(mtype));
		}
	}

	private void initPreviousMachinesButtonActionListener() {
		this.marketPanel
				.addPreviousMachinesButtonActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						LineElementsMarketPanelController.this
								.decreseMachineTab();

					}
				});
	}

	private void initNextMachinesButtonActionListener() {
		this.marketPanel
				.addNextMachinesButtonActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						LineElementsMarketPanelController.this
								.increaseMachineTab();
					}
				});
	} 
	
	private boolean canIncreseMachineTab() {

		int buttonsSize = this.marketPanel.getMachineButtonsSize();
		int machinesSize = this.machineButtonsImages.size();

		return machinesSize / buttonsSize >= this.machineTab + 1;
	}

	private boolean canDecreseMachineTab() {
		return this.machineTab - 1 >= 0;
	}

	private void increaseMachineTab() {
		this.machineTab++;
		this.setUpButtons();
	}

	private void decreseMachineTab() {
		this.machineTab--;
		this.setUpButtons();
	}

	/**
	 * Determines the buttons that has to be shown on the panel.
	 */
	private void setUpButtons() {

		// Next-Previous machine buttons.
		this.marketPanel.setNextMachineButtonEnabled(this
				.canIncreseMachineTab());
		this.marketPanel.setPreviousMachineButtonEnabled(this
				.canDecreseMachineTab());

		// Machine buttons.
		int buttonsSize = this.marketPanel.getMachineButtonsSize();
		int machinesSize = this.machineButtonsImages.size();

		int startIndex = buttonsSize * this.machineTab;
		int lastIndex = machinesSize >= startIndex + buttonsSize ? startIndex
				+ buttonsSize : machinesSize;

		int j = 0;
		for (int i = startIndex; i < lastIndex; i++, j++) {
			Action action = this.machineButtonsActions.get(i);
			ImageIcon icon = new ImageIcon(this.machineButtonsImages.get(i));

			this.marketPanel.setMachineButtonActionListener(j, action);
			this.marketPanel.setMachineButtonIcon(j, icon);
			this.marketPanel.setMachineButtonVisible(j, true);
		}
		for (; j < buttonsSize; j++) {
			this.marketPanel.setMachineButtonVisible(j, false);
		}
	}
}
