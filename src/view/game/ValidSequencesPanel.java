package view.game;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

public class ValidSequencesPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
	
	/** Creates new form ValidSequencesPanel */
    public ValidSequencesPanel() {
        initComponents();
    }

    public JComboBox getValidSequencesCombo(){
        return this.validSequenceCombo;
    }

    public JTable getRawMaterialNeededTable(){
        return this.rawMaterialsTable;
    }

    public JList getSequencesList(){
        return this.sequencesList;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        storageAreaLabel = new javax.swing.JLabel();
        validSequenceCombo = new javax.swing.JComboBox();
        productLabel = new javax.swing.JLabel();
        sequencesLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sequencesList = new javax.swing.JList();
        rawMaterialLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rawMaterialsTable = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(227, 539));
        setMinimumSize(new java.awt.Dimension(227, 539));
        setPreferredSize(new java.awt.Dimension(227, 539));

        storageAreaLabel.setFont(new java.awt.Font("Purisa", 0, 18));
        storageAreaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        storageAreaLabel.setText("Valid Sequences");

        validSequenceCombo.setFont(new java.awt.Font("Purisa", 0, 13));

        productLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        productLabel.setText("Product:");

        sequencesLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        sequencesLabel.setText("Sequences:");

        sequencesList.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        sequencesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(sequencesList);

        rawMaterialLabel.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        rawMaterialLabel.setText("Raw materials needed:");

        rawMaterialsTable.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        rawMaterialsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(rawMaterialsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(storageAreaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(validSequenceCombo, 0, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sequencesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rawMaterialLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(storageAreaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(validSequenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sequencesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rawMaterialLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel productLabel;
    private javax.swing.JLabel rawMaterialLabel;
    private javax.swing.JTable rawMaterialsTable;
    private javax.swing.JLabel sequencesLabel;
    private javax.swing.JList sequencesList;
    private javax.swing.JLabel storageAreaLabel;
    private javax.swing.JComboBox validSequenceCombo;
    // End of variables declaration//GEN-END:variables

}
