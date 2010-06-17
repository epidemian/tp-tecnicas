package controller.game;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;

import model.game.Game;
import static model.utils.ArgumentUtils.*;
import model.production.elements.machine.MachineType;

import view.game.LineElementsMarketPanel;
import view.game.TileElementImageRecognizer;
import view.game.edition.EditionActions;

public class LineElementsMarketPanelController {

	private List<Action> machineButtonsActions;
	private List<ImageIcon> machineButtonsImages;

	private EditionActions editionActions;
	private LineElementsMarketPanel marketPanel;
	private int machineTab;

	public LineElementsMarketPanelController(Game game,
			LineElementsMarketPanel marketPanel, EditionActions editionActions) {

		checkNotNull(editionActions, "editionActions");
		checkNotNull(marketPanel, "marketPanel");

		this.editionActions = editionActions;
		this.marketPanel = marketPanel;

		this.machineButtonsActions = new ArrayList<Action>();
		this.machineButtonsImages = new ArrayList<ImageIcon>();

		this.machineTab = 0;

		initNextMachinesButtonActionListener();
		initPreviousMachinesButtonActionListener();

		// Conveyor action.
		this.marketPanel.addConveyorButtonActionListener(this.editionActions
				.getActionToSetConveyorTool());

		// Raw materials input action.
		this.marketPanel.addInputButtonActionListener(this.editionActions
				.getActionToSetInputLineElementTool());

		initMachineButtonsActionsAndImages(game);

		this.setUpButtons();
	}

	private void initMachineButtonsActionsAndImages(Game game) {
		// Production machine buttons actions n' images.
		for (MachineType mtype : game.getProductionMachinesTypes()) {
			ImageIcon icon = getScaleImage(TileElementImageRecognizer
					.getMachineImage(mtype));

			this.machineButtonsImages.add(icon);
			this.machineButtonsActions.add(this.editionActions
					.getActionToSetNewProductionMachineTool(mtype));
		}

		// Quality control machine buttons actions n' images.
		for (MachineType mtype : game.getQualityControlMachinesTypes()) {
			ImageIcon icon = getScaleImage(TileElementImageRecognizer
					.getMachineImage(mtype));

			this.machineButtonsImages.add(icon);
			this.machineButtonsActions.add(this.editionActions
					.getActionToSetNewQualityControlMachineTool(mtype));
		}
	}

	static private ImageIcon getScaleImage(BufferedImage image) {
		Dimension image_size = new Dimension(50, 35);

		Image scaleImage = new ImageIcon(image).getImage().getScaledInstance(
				image_size.width, image_size.height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaleImage);
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
			ImageIcon icon = this.machineButtonsImages.get(i);

			this.marketPanel.setMachineButtonActionListener(j, action);
			this.marketPanel.setMachineButtonIcon(j, icon);
			this.marketPanel.setMachineButtonVisible(j, true);
		}
		for (; j < buttonsSize; j++) {
			this.marketPanel.setMachineButtonVisible(j, false);
		}
	}
}
