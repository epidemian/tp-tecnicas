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
import view.game.BackGroundPanel;

public class LineElementsMarketPanelController {

	private List<Action> actions;
	private List<BufferedImage> images;
        private List<String> messages;
        
        private Action selectionTool;
        private Action deleteTool;

        private JButton sellButton;
        private JButton repairButton;
        private JButton moveButton;
        private JButton cancelButon;

        private JComboBox lineElements;

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

                this.sellButton = marketPanel.getSellButton();
                this.repairButton = marketPanel.getRepairButton();
                this.moveButton = marketPanel.getMoveButton();
                this.cancelButon = marketPanel.getCancelButton();
                this.lineElements = marketPanel.getBuyCombo();

                this.initBuyCombo(marketPanel);
                this.initToolButtons();
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

        lineElements.removeAllItems();
        lineElements.addItem(new LineElementComboEntry(-1,"-"));

        int i = 0;
	for (String lineElementMessage : this.messages)
            lineElements.addItem(new LineElementComboEntry(i++, lineElementMessage));

        lineElements.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                lineElementsComboAction(marketPanel);
            }
        });
    }


    private void lineElementsComboAction(LineElementsMarketPanel
            marketPanel) {

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

        enabledButtons(false);
        cancelButon.setEnabled(true);
    }

    private void initToolButtons() {

        cancelButon.setEnabled(false);
        
        // Sell.
        sellButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledButtons(false);
                enabledCombo(false);
                sellButton.setEnabled(true);
                cancelButon.setEnabled(true);
                deleteTool.actionPerformed(e);
            }
        });


        // Move.
        moveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledButtons(false);
                enabledCombo(false);
                moveButton.setEnabled(true);
                cancelButon.setEnabled(true);
                selectionTool.actionPerformed(e);
            }
        });

        // Repair.
        repairButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledButtons(false);
                enabledCombo(false);
                repairButton.setEnabled(true);
                cancelButon.setEnabled(true);
                
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        // Cancel.
        cancelButon.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enabledCombo(true);
                enabledButtons(true);
                cancelButon.setEnabled(false);
                selectionTool.actionPerformed(e);
            }
        });
    }

    private void enabledButtons(boolean aFlag){

        moveButton.setEnabled(aFlag);
        sellButton.setEnabled(aFlag);
        cancelButon.setEnabled(aFlag);
        repairButton.setEnabled(aFlag);
    }

    private void enabledCombo(boolean aFlag){
        lineElements.setSelectedIndex(0);
        lineElements.setEnabled(aFlag);
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
