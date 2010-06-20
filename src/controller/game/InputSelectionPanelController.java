package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import model.game.Player;
import model.production.RawMaterialType;
import model.production.RawMaterials;
import static model.utils.ArgumentUtils.*;
import model.production.elements.InputProductionLineElement;
import view.game.InputSelectionPanel;
import view.game.ViewUtils;

public class InputSelectionPanelController {

    private static final String[] columnNames = { "Type", "Quantity" };

    private InputProductionLineElement input;
    private RawMaterialType selectedRawMaterial;
    private int rawMaterialQuantity;

    public InputSelectionPanelController(InputProductionLineElement input,
        InputSelectionPanel inputPanel, Player player){

        checkNotNull(input, "input");
        checkNotNull(inputPanel, "inputPanel");
        checkNotNull(player, "player");

        this.input = input;

        initRawMaterialCombo(inputPanel,player.getValidRawMaterialTypes());
        initRawMaterialSpinner(inputPanel);
        initRawMaterialTable(inputPanel);
        initAddRawMaterialButton(inputPanel);
        initDeleteRawMaterialButton(inputPanel);
    }

    private void initRawMaterialCombo(InputSelectionPanel inputPanel,
            List<RawMaterialType> validRawMaterialTypes) {

        final JComboBox inputRawMaterial = inputPanel.getRawMaterialCombo();

        inputRawMaterial.removeAllItems();
	for (RawMaterialType rtype : validRawMaterialTypes)
            inputRawMaterial.addItem(new RawMaterialTypeComboEntry(rtype));

        rawMaterialComboAction(inputRawMaterial);

        inputRawMaterial.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                rawMaterialComboAction(inputRawMaterial);
            }
        });
    }
    
    private void rawMaterialComboAction(JComboBox inputRawMaterial){
        
        RawMaterialTypeComboEntry rawMaterialEntry =
            (RawMaterialTypeComboEntry)inputRawMaterial.getSelectedItem();
        this.selectedRawMaterial = rawMaterialEntry.getRawMaterialType();
    }

    private void initRawMaterialSpinner(InputSelectionPanel inputPanel) {

        int value = 0;
        int minimum = 0;
        int maximun = Integer.MAX_VALUE;
        int stepSize = 1;

        SpinnerNumberModel model = new SpinnerNumberModel(value, minimum,
            maximun, stepSize);
        final JSpinner spinner = inputPanel.getQuantitySpinner();
        spinner.setModel(model);

        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                rawMaterialQuantity = (Integer)(spinner.getValue());
            }
        });
    }

    private void initRawMaterialTable(InputSelectionPanel inputPanel) {

        JTable table = inputPanel.getConfigTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        updateRawMaterialTable(inputPanel);
    }

    private void updateRawMaterialTable(InputSelectionPanel inputPanel){
         JTable table = inputPanel.getConfigTable();
         DefaultTableModel model = (DefaultTableModel) table.getModel();

         // Clears the table.
         model.setRowCount(0);
         model.setColumnCount(0);

         model.addColumn(columnNames[0]);
         model.addColumn(columnNames[1]);

         Map<RawMaterialType, Integer> rawMaterials =
            this.input.getRawMaterialsConfiguration().getRawMaterials();

        for (Entry<RawMaterialType, Integer> entry : rawMaterials.entrySet())
                model.addRow(new Object[] { entry.getKey(), entry.getValue() });

        ViewUtils.autoResizeColWidth(table, model);
    }

    private void initAddRawMaterialButton(final InputSelectionPanel inputPanel) {

        JButton add = inputPanel.getAddlButton();

        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                RawMaterials config = input.getRawMaterialsConfiguration();
                config.put(selectedRawMaterial, rawMaterialQuantity);
                updateRawMaterialTable(inputPanel);
            }
        });
    }

    private void initDeleteRawMaterialButton(final InputSelectionPanel inputPanel) {

        JButton delete = inputPanel.getDeleteButton();

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               JTable table = inputPanel.getConfigTable();
  
               int row = table.getSelectedRow();
               int col = 0;

               // row = -1 when the table is empty.
               if (row >= 0){
                    RawMaterialType rtype = (RawMaterialType) table.getValueAt(row, col);
                    input.getRawMaterialsConfiguration().remove(rtype);
                    updateRawMaterialTable(inputPanel);
               }
            }
        });
    }

    private class RawMaterialTypeComboEntry {

        private RawMaterialType rawMaterialType;

        public RawMaterialTypeComboEntry(RawMaterialType rawMaterialType){
            checkNotNull(rawMaterialType, "rawMaterialType");
            this.rawMaterialType = rawMaterialType;
        }

        public RawMaterialType getRawMaterialType(){
            return this.rawMaterialType;
        }

        @Override
        public String toString(){
            return rawMaterialType.getName();
        }
    }
}
