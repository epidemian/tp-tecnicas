package view.game;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;

public class InputSelectionPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	public InputSelectionPanel() {
        initComponents();
    }

    public JButton getAddlButton(){
        return this.addButton;
    }

    public JButton getDeleteButton(){
        return this.deleteButton;
    }

    public JTable getConfigTable(){
        return this.configTable;
    }

    public JComboBox getRawMaterialCombo(){
        return this.rawMaterialCombo;
    }

    public JSpinner getQuantitySpinner(){
        return this.quantitySpinner;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tittleLabel = new javax.swing.JLabel();
        configLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        configTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();
        changeConfigLabel = new javax.swing.JLabel();
        rawMaterialCombo = new javax.swing.JComboBox();
        quantitySpinner = new javax.swing.JSpinner();
        addButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(227, 539));
        setMinimumSize(new java.awt.Dimension(227, 539));
        setPreferredSize(new java.awt.Dimension(227, 539));

        tittleLabel.setFont(new java.awt.Font("Purisa", 0, 18));
        tittleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tittleLabel.setText("Input");
        tittleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        configLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        configLabel.setText("Raw material configuration:");

        configTable.setFont(new java.awt.Font("Purisa", 0, 13));
        configTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        configTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(configTable);

        deleteButton.setFont(new java.awt.Font("Purisa", 0, 13));
        deleteButton.setText("Delete raw material");

        changeConfigLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        changeConfigLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        changeConfigLabel.setText("Change configuration:");
        changeConfigLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        rawMaterialCombo.setFont(new java.awt.Font("Purisa", 0, 13));

        quantitySpinner.setFont(new java.awt.Font("Purisa", 0, 13));

        addButton.setFont(new java.awt.Font("Purisa", 0, 13));
        addButton.setText("Add raw material");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(configLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addComponent(tittleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                            .addComponent(changeConfigLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                            .addComponent(rawMaterialCombo, 0, 198, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(deleteButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(configLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeConfigLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rawMaterialCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton))
                .addContainerGap(98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel changeConfigLabel;
    private javax.swing.JLabel configLabel;
    private javax.swing.JTable configTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner quantitySpinner;
    private javax.swing.JComboBox rawMaterialCombo;
    private javax.swing.JLabel tittleLabel;
    // End of variables declaration//GEN-END:variables

}
