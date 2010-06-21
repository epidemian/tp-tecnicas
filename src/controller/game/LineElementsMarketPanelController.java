package controller.game;

import java.awt.event.ActionEvent;
import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import model.game.Player;
import model.production.elements.machine.MachineType;
import view.game.LineElementsMarketPanel;
import view.game.TileElementImageRecognizer;
import controller.game.edition.EditionActions;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;

public class LineElementsMarketPanelController {

	private List<Action> actions;
	private List<BufferedImage> images;
        private List<String> messages;
        
        private Action selectionTool;
        private Action deleteTool;

	public LineElementsMarketPanelController(Player game,
			LineElementsMarketPanel marketPanel, EditionActions editionActions) {

                checkNotNull(game, "game");
		checkNotNull(marketPanel, "marketPanel");
		checkNotNull(editionActions, "editionActions");

                this.selectionTool = editionActions.getActionToSetSelectionTool();
                this.deleteTool = editionActions.getActionToSetDeleteTool();

		this.actions = new ArrayList<Action>();
		this.images = new ArrayList<BufferedImage>();
                this.messages = new ArrayList<String>();

                this.initConveyor(editionActions);
                this.initInput(editionActions);
                this.initProductionMachines(editionActions,game.getValidProductionMachineTypes());
                this.initQualityMachines(editionActions,game.getValidQualityControlMachineTypes());

                this.initBuyCombo(marketPanel);
                this.initToolButtons(marketPanel);
	}

        private void initConveyor(EditionActions editionActions){
            BufferedImage image = TileElementImageRecognizer
                    .getConveyorImage(new Conveyor());
            Action action = editionActions
                    .getActionToSetConveyorTool();

            this.images.add(image);
            this.actions.add(action);
            this.messages.add("Conveyor");
        }

        private void initInput(EditionActions editionActions){
             BufferedImage image = TileElementImageRecognizer
                     .getInputElementImage(new InputProductionLineElement());
            Action action = editionActions
                    .getActionToSetInputLineElementTool();

            this.images.add(image);
            this.actions.add(action);
            this.messages.add("Input");
        }

        private void initProductionMachines(EditionActions editionActions,
            List<MachineType> validProductionMachineTypes){
             for (MachineType mtype : validProductionMachineTypes) {
                BufferedImage image = TileElementImageRecognizer
                    .getMachineImage(mtype);
                Action action = editionActions
                    .getActionToSetNewProductionMachineTool(mtype);

                this.images.add(image);
                this.actions.add(action);
                this.messages.add("Machine - " + mtype.getName());
            }
        }

        private void initQualityMachines(EditionActions editionActions,
            List<MachineType> validQualityMachineTypes){
            for (MachineType mtype : validQualityMachineTypes) {
                BufferedImage image = TileElementImageRecognizer
                        .getMachineImage(mtype);
                Action action = editionActions
                        .getActionToSetNewQualityControlMachineTool(mtype);

                this.images.add(image);
                this.actions.add(action);
                this.messages.add("Machine - " + mtype.getName());
            }
        }


    private void initBuyCombo(final LineElementsMarketPanel marketPanel) {

        final JComboBox lineElements = marketPanel.getBuyCombo();

        lineElements.removeAllItems();
        lineElements.addItem(new LineElementComboEntry(-1,"-"));

        int i = 0;
	for (String lineElementMessage : this.messages)
            lineElements.addItem(new LineElementComboEntry(i++, lineElementMessage));

        lineElements.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                rawMaterialComboAction(lineElements, marketPanel);
            }
        });
    }


    private void rawMaterialComboAction(JComboBox lineElements,
            LineElementsMarketPanel marketPanel) {

        LineElementComboEntry lineElementEntry =
            (LineElementComboEntry)lineElements.getSelectedItem();

        int index = lineElementEntry.getIndex();

        // index = -1, null entry!
        if (index >= 0){
            BufferedImage image = this.images.get(index);
            String message = this.messages.get(index);
            Action action = this.actions.get(index);

            marketPanel.setLineElementImage(image);
            marketPanel.setLineElementType(message);
            action.actionPerformed(null);
        }
        else{
            marketPanel.setLineElementImage(null);
            marketPanel.setLineElementType(lineElementEntry.toString());
            this.selectionTool.actionPerformed(null);
        }
    }

    private void initToolButtons(final LineElementsMarketPanel marketPanel) {

        final JButton sellButton = marketPanel.getSellButton();
        final JButton repairButton = marketPanel.getRepairButton();
        final JButton moveButton = marketPanel.getMoveButton();
        final JButton cancelButon = marketPanel.getCancelButton();

        // Sell.
        sellButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledAllPanelElements(marketPanel, false);
                sellButton.setEnabled(true);
                cancelButon.setEnabled(true);
                deleteTool.actionPerformed(e);
            }
        });


        // Move.
        moveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledAllPanelElements(marketPanel, false);
                moveButton.setEnabled(true);
                cancelButon.setEnabled(true);
                selectionTool.actionPerformed(e);
            }
        });

        // Repair.
        repairButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledAllPanelElements(marketPanel, false);
                repairButton.setEnabled(true);
                cancelButon.setEnabled(true);
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        // Cancel.
        cancelButon.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledAllPanelElements(marketPanel, true);
                selectionTool.actionPerformed(e);
            }
        });
    }

    private void enabledAllPanelElements(LineElementsMarketPanel marketPanel,
        boolean aFlag){

        marketPanel.getMoveButton().setEnabled(aFlag);
        marketPanel.getSellButton().setEnabled(aFlag);
        marketPanel.getCancelButton().setEnabled(aFlag);
        marketPanel.getRepairButton().setEnabled(aFlag);

        JComboBox combo = marketPanel.getBuyCombo();
        combo.setSelectedIndex(0);
        combo.setEnabled(aFlag);
    }

    private class LineElementComboEntry{

        private String message;
        private int index;

        public LineElementComboEntry(int index, String message){
            checkNotNull(message, "message");
            this.message = message;
            this.index = index;
        }

        public int getIndex(){
            return this.index;
        }

        @Override
        public String toString(){
            return this.message;
        }
    }
}
