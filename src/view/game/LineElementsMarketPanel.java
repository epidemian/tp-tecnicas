package view.game;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LineElementsMarketPanel extends javax.swing.JPanel {

    public LineElementsMarketPanel() {

        initComponents();
        this.machineButtons = new ArrayList<JButton>();

        this.machineButtons.add(this.machine1);
        this.machineButtons.add(this.machine2);
        this.machineButtons.add(this.machine3);
        this.machineButtons.add(this.machine4);
        this.machineButtons.add(this.machine5);
        this.machineButtons.add(this.machine6);
        this.machineButtons.add(this.machine7);
        this.machineButtons.add(this.machine8);
        this.machineButtons.add(this.machine9);
        this.machineButtons.add(this.machine10);
        this.machineButtons.add(this.machine11);
        this.machineButtons.add(this.machine12);
        this.machineButtons.add(this.machine13);
        this.machineButtons.add(this.machine14);
        this.machineButtons.add(this.machine15);
    }
        
    public void setMachineButtonIcon(int index, ImageIcon image){
    	this.machineButtons.get(index).setIcon(image);
    }
    
    public void setMachineButtonVisible(int index, boolean aFlag){
    	this.machineButtons.get(index).setVisible(aFlag);
    }
    
    public void setMachineButtonActionListener(int index, ActionListener action){
    	JButton machineButton = this.machineButtons.get(index);
        removeActionListenerFromButton(machineButton);
    	machineButton.addActionListener(action);
    }

    public int getMachineButtonsSize(){
    	return this.machineButtons.size();
    }
    
    public void addNextMachinesButtonActionListener(ActionListener action){
    	this.nextMachine.addActionListener(action);
    }
    
    public void setNextMachineButtonEnabled(boolean aFlag){
    	this.nextMachine.setEnabled(aFlag);
    }
    
    public void addPreviousMachinesButtonActionListener(ActionListener action){
    	this.previousMachine.addActionListener(action);
    }
    
    public void setPreviousMachineButtonEnabled(boolean aFlag){
    	this.previousMachine.setEnabled(aFlag);
    }
    
    public void addConveyorButtonActionListener(ActionListener action){
    	this.conveyor.addActionListener(action);
    }
    
    public void addInputButtonActionListener(ActionListener action){
    	this.input.addActionListener(action);
    }
    
    public void setLabelDescriptionImage(BufferedImage image){
        this.descriptionImage.setImage(image);
    }

    // TODO ver si cambiar de lugar! o si hay otra forma de hacerlo!
    private static void removeActionListenerFromButton(JButton button){
        ActionListener[] actionListeners = button.getActionListeners();
        for (int i = 0; i < actionListeners.length; i++)
            button.removeActionListener(actionListeners[i]);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        machineButtonsPanel = new javax.swing.JPanel();
        machine1 = new javax.swing.JButton();
        machine2 = new javax.swing.JButton();
        machine3 = new javax.swing.JButton();
        machine4 = new javax.swing.JButton();
        machine5 = new javax.swing.JButton();
        machine6 = new javax.swing.JButton();
        machine7 = new javax.swing.JButton();
        machine8 = new javax.swing.JButton();
        machine9 = new javax.swing.JButton();
        machine10 = new javax.swing.JButton();
        machine11 = new javax.swing.JButton();
        machine12 = new javax.swing.JButton();
        machine13 = new javax.swing.JButton();
        machine14 = new javax.swing.JButton();
        machine15 = new javax.swing.JButton();
        machineNextPreviousPanel = new javax.swing.JPanel();
        previousMachine = new javax.swing.JButton();
        nextMachine = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        conveyorLabel = new javax.swing.JLabel();
        conveyor = new javax.swing.JButton();
        inputLabel = new javax.swing.JLabel();
        input = new javax.swing.JButton();
        machineLabel = new javax.swing.JLabel();
        descriptionImage = new view.game.BackGroundPanel();

        machineButtonsPanel.setLayout(new java.awt.GridLayout(5, 3));
        machineButtonsPanel.add(machine1);
        machineButtonsPanel.add(machine2);
        machineButtonsPanel.add(machine3);
        machineButtonsPanel.add(machine4);
        machineButtonsPanel.add(machine5);
        machineButtonsPanel.add(machine6);
        machineButtonsPanel.add(machine7);
        machineButtonsPanel.add(machine8);
        machineButtonsPanel.add(machine9);
        machineButtonsPanel.add(machine10);
        machineButtonsPanel.add(machine11);
        machineButtonsPanel.add(machine12);
        machineButtonsPanel.add(machine13);
        machineButtonsPanel.add(machine14);
        machineButtonsPanel.add(machine15);

        machineNextPreviousPanel.setLayout(new java.awt.GridLayout(1, 2));

        previousMachine.setFont(new java.awt.Font("Purisa", 0, 13));
        previousMachine.setText("Previous");
        machineNextPreviousPanel.add(previousMachine);

        nextMachine.setFont(new java.awt.Font("Purisa", 0, 13));
        nextMachine.setText("Next");
        machineNextPreviousPanel.add(nextMachine);

        titleLabel.setFont(new java.awt.Font("Purisa", 0, 15));
        titleLabel.setText("Line Elements");

        conveyorLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        conveyorLabel.setText("Conveyor");

        conveyor.setPreferredSize(new java.awt.Dimension(66, 29));

        inputLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        inputLabel.setText("Input");

        machineLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        machineLabel.setText("Machines");

        javax.swing.GroupLayout descriptionImageLayout = new javax.swing.GroupLayout(descriptionImage);
        descriptionImage.setLayout(descriptionImageLayout);
        descriptionImageLayout.setHorizontalGroup(
            descriptionImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        descriptionImageLayout.setVerticalGroup(
            descriptionImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(machineNextPreviousPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(machineButtonsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(descriptionImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(conveyorLabel)
                            .addComponent(inputLabel)
                            .addComponent(machineLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(conveyor, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(titleLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conveyorLabel)
                    .addComponent(conveyor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLabel)
                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(machineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(machineButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(machineNextPreviousPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(descriptionImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private List<JButton> machineButtons;
	private static final long serialVersionUID = 1L;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton conveyor;
    private javax.swing.JLabel conveyorLabel;
    private view.game.BackGroundPanel descriptionImage;
    private javax.swing.JButton input;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JButton machine1;
    private javax.swing.JButton machine10;
    private javax.swing.JButton machine11;
    private javax.swing.JButton machine12;
    private javax.swing.JButton machine13;
    private javax.swing.JButton machine14;
    private javax.swing.JButton machine15;
    private javax.swing.JButton machine2;
    private javax.swing.JButton machine3;
    private javax.swing.JButton machine4;
    private javax.swing.JButton machine5;
    private javax.swing.JButton machine6;
    private javax.swing.JButton machine7;
    private javax.swing.JButton machine8;
    private javax.swing.JButton machine9;
    private javax.swing.JPanel machineButtonsPanel;
    private javax.swing.JLabel machineLabel;
    private javax.swing.JPanel machineNextPreviousPanel;
    private javax.swing.JButton nextMachine;
    private javax.swing.JButton previousMachine;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
