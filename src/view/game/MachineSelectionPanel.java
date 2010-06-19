package view.game;

import java.awt.image.BufferedImage;
import javax.swing.JButton;

public class MachineSelectionPanel extends javax.swing.JPanel {

    public MachineSelectionPanel() {
        initComponents();
    }

    public void setProductionMachineLabels(){
        this.tittleLabel.setText(PRODUCTION);
    }

    public void setQualityControlMachineLabels(){
        this.tittleLabel.setText(QUALITY);
    }

    public void setMachinePrice(int price){
        this.machinePriceLabel.setText("" + price);
    }

    public void setMachineState(String state){
        this.machineStateLabel.setText(state);
    }

    public void setFailProductProcessChance(double fail){
        this.machineFailLabel.setText("" + fail);
    }

    public void setMachineImage(BufferedImage image){
        this.machineImage.setImage(image);
    }

    public void setMachineType(String type){
        this.machineTypeLabel.setText(type);
    }

    public JButton getSellButton(){
        return this.sellButton;
    }

    public JButton getMoveButton(){
        return this.moveButton;
    }

    public JButton getRepairButton(){
        return this.repairButton;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tittleLabel = new javax.swing.JLabel();
        machineImage = new view.game.BackGroundPanel();
        buttonsExternalPanel = new javax.swing.JPanel();
        buttonsPanel = new javax.swing.JPanel();
        sellButton = new javax.swing.JButton();
        moveButton = new javax.swing.JButton();
        repairButton = new javax.swing.JButton();
        infoPanel = new javax.swing.JPanel();
        failLabel = new javax.swing.JLabel();
        machineFailLabel = new javax.swing.JLabel();
        machineTypeLabel = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();
        machineStateLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        machinePriceLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(227, 539));
        setMinimumSize(new java.awt.Dimension(227, 539));
        setPreferredSize(new java.awt.Dimension(227, 539));

        tittleLabel.setFont(new java.awt.Font("Purisa", 0, 18));
        tittleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tittleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout machineImageLayout = new javax.swing.GroupLayout(machineImage);
        machineImage.setLayout(machineImageLayout);
        machineImageLayout.setHorizontalGroup(
            machineImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );
        machineImageLayout.setVerticalGroup(
            machineImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );

        buttonsPanel.setLayout(new java.awt.GridLayout(1, 3));

        sellButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smallSell.png"))); // NOI18N
        sellButton.setToolTipText("Sell machine");
        buttonsPanel.add(sellButton);

        moveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smallMove.png"))); // NOI18N
        moveButton.setToolTipText("Move machine");
        buttonsPanel.add(moveButton);

        repairButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smallRepair.png"))); // NOI18N
        repairButton.setToolTipText("Repair machine");
        buttonsPanel.add(repairButton);

        javax.swing.GroupLayout buttonsExternalPanelLayout = new javax.swing.GroupLayout(buttonsExternalPanel);
        buttonsExternalPanel.setLayout(buttonsExternalPanelLayout);
        buttonsExternalPanelLayout.setHorizontalGroup(
            buttonsExternalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsExternalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        buttonsExternalPanelLayout.setVerticalGroup(
            buttonsExternalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsExternalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        failLabel.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        failLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        failLabel.setText("Fail Process Chance:");

        machineFailLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        machineFailLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        machineTypeLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        machineTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        stateLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        stateLabel.setText("State:");

        machineStateLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        machineStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        priceLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        priceLabel.setText("Sale price:");

        typeLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        typeLabel.setText("Type:");

        machinePriceLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        machinePriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(machineTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(machinePriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(machineStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(failLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(machineFailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(machineTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(machinePriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(priceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(machineStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(failLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(machineFailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tittleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(machineImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(buttonsExternalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(machineImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonsExternalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private static final String PRODUCTION = "Production Machine";
    private static final String QUALITY = "Quality Machine";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsExternalPanel;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JLabel failLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel machineFailLabel;
    private view.game.BackGroundPanel machineImage;
    private javax.swing.JLabel machinePriceLabel;
    private javax.swing.JLabel machineStateLabel;
    private javax.swing.JLabel machineTypeLabel;
    private javax.swing.JButton moveButton;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton repairButton;
    private javax.swing.JButton sellButton;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JLabel tittleLabel;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables

}
