package view.game;

import javax.swing.JPanel;

public class GamePanel extends javax.swing.JPanel {
    
    /** Creates new form GamePanel */
    public GamePanel(GroundPanelContainer groundPanelContainer) {
        this.groundPanelContainer = groundPanelContainer;
        initComponents();

        this.toolBarPanel.setMaximumSize(this.toolBarPanel.getPreferredSize()); // prevent growth
        this.toolBarPanel.setMinimumSize(this.toolBarPanel.getPreferredSize()); // prevent shrink
    }
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        toolBarPanel = new view.game.ToolBarPanel();
        budgetPanel = new view.game.BudgetPanel();
        timeLabel = new javax.swing.JLabel();
        toolPanel = new javax.swing.JPanel();
        jScrollPane1 = this.groundPanelContainer;

        setFont(new java.awt.Font("Purisa", 0, 13));
        setPreferredSize(new java.awt.Dimension(850, 650));
        setLayout(new java.awt.GridBagLayout());

        timeLabel.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("jLabel1");
        timeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(toolBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addComponent(budgetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(budgetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 149;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        add(jPanel1, gridBagConstraints);

        toolPanel.setPreferredSize(new java.awt.Dimension(227, 539));
        toolPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 539;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 12, 0);
        add(toolPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 659;
        gridBagConstraints.ipady = 515;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 12, 12);
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    public ToolBarPanel getToolBarPanel(){
        return this.toolBarPanel;
    }

    public void setToolPanel(JPanel panel){
        this.toolPanel.removeAll();
        this.toolPanel.add(panel);
        this.toolPanel.revalidate();
        this.toolPanel.repaint();
    }

    public GroundPanelContainer getGroundPanelContainer(){
        return this.groundPanelContainer;
    }

    public BudgetPanel getBudgetPanel(){
        return this.budgetPanel;
    }

    public void setTimeLabel(String time){
        this.timeLabel.setText(time);
    }

    private GroundPanelContainer groundPanelContainer;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.game.BudgetPanel budgetPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel timeLabel;
    private view.game.ToolBarPanel toolBarPanel;
    private javax.swing.JPanel toolPanel;
    // End of variables declaration//GEN-END:variables
}
