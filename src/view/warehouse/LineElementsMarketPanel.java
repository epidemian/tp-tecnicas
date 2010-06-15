/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LineElementsMarketPanel.java
 *
 * Created on 14/06/2010, 14:29:13
 */

package view.warehouse;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import model.production.MachineType;
import view.warehouse.edition.EditionActions;

/**
 *
 * @author nicolas
 */
public class LineElementsMarketPanel extends javax.swing.JPanel {

    public LineElementsMarketPanel(){
         initComponents();
    }

    /** Creates new form LineElementsMarketPanel */
    public LineElementsMarketPanel(List<MachineType> productionMachines,
            List<MachineType> qualityControlMachines,
            EditionActions editionActions) {

        initComponents();
        this.machineButtonsImages = new ArrayList<ImageIcon>();

        this.machineButtonsActions = new ArrayList<Action>();
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

        // Conveyor image.
        // Input image.

        // Init production machine images and actions.
        for (MachineType mtype : productionMachines){
            this.machineButtonsImages.add(getScaleImageIcon(mtype));
            this.machineButtonsActions.add(
                editionActions.getActionToSetNewMachineTool(mtype));
        }

        // Init quality control machine images and actions.
        for (MachineType mtype : qualityControlMachines){
            this.machineButtonsImages.add(getScaleImageIcon(mtype));
            /*
             * TODO
             * Action para quality control distintas?
             */
        }

        this.conveyor.addActionListener(
              editionActions.getActionToSetConveyorTool());
    
        /*
         * Action para el input!    
         */


        // Sets up action listener to previous line element button.
        this.nextMachine.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                    LineElementsMarketPanel.this.decreseLineElementsTab();
            }
        });

        // Sets up action listener to next line element button.
        this.previousMachine.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                    LineElementsMarketPanel.this.increseLineElementsTab();
            }
        });
    }

    private void increseLineElementsTab() {
        int lineElementsSize = this.machineButtonsImages.size();
        if (lineElementsSize / this.machineButtonsSize >= this.machineTab + 1)
                this.machineTab++;
        this.setUpMachineButtons();
    }

    private void decreseLineElementsTab() {
        if (this.machineTab - 1 >= 0)
                this.machineTab--;
        this.setUpMachineButtons();
    }

    private static ImageIcon getScaleImageIcon(MachineType machine) {

        final Dimension image_size = new Dimension(50,35);
        BufferedImage image = TileElementImageRecognizer.getMachineImage(machine);
        Image scaleImage = new ImageIcon(image).getImage().getScaledInstance(image_size.width, image_size.height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaleImage);
    }

    private void setUpMachineButtons() {
        int startIndex = this.machineButtonsSize * this.machineTab;
        int lastIndex = this.machineButtonsImages.size() >= startIndex
                        + this.machineButtonsSize ? startIndex
                        + this.machineButtonsSize : this.machineButtonsImages.size();

        int j = 0;
        for (int i = startIndex; i < lastIndex; i++, j++){
            JButton button = this.machineButtons.get(j);
            button.setVisible(true);
            button.setIcon(this.machineButtonsImages.get(i));
            button.addActionListener(null);
        }
        for (; j < this.machineButtonsSize; j++) {
            this.machineButtons.get(j).setVisible(false);
        }
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
        backGroundPanel1 = new view.warehouse.BackGroundPanel();
        tittleLabel = new javax.swing.JLabel();
        conveyorLabel = new javax.swing.JLabel();
        conveyor = new javax.swing.JButton();
        inputLabel = new javax.swing.JLabel();
        input = new javax.swing.JButton();
        machineLabel = new javax.swing.JLabel();

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
        machineNextPreviousPanel.add(previousMachine);
        machineNextPreviousPanel.add(nextMachine);

        javax.swing.GroupLayout backGroundPanel1Layout = new javax.swing.GroupLayout(backGroundPanel1);
        backGroundPanel1.setLayout(backGroundPanel1Layout);
        backGroundPanel1Layout.setHorizontalGroup(
            backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        backGroundPanel1Layout.setVerticalGroup(
            backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 107, Short.MAX_VALUE)
        );

        tittleLabel.setFont(new java.awt.Font("Purisa", 0, 15)); // NOI18N
        tittleLabel.setText("Production Line Elements");

        conveyorLabel.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        conveyorLabel.setText("Conveyor");

        conveyor.setPreferredSize(new java.awt.Dimension(66, 29));

        inputLabel.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        inputLabel.setText("Input");

        machineLabel.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        machineLabel.setText("Machines");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(conveyorLabel)
                                    .addComponent(inputLabel)
                                    .addComponent(machineLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(conveyor, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backGroundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(machineNextPreviousPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(machineButtonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(backGroundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Number of buttons permitted.
    private final int machineButtonsSize = 15;
    // Determinate the tab of the panel that has the buttons.
    private int machineTab = 0;

    private List<JButton> machineButtons;

    private List<ImageIcon> machineButtonsImages;
    private ImageIcon conveyorImage;
    private ImageIcon inputImage;

    private List<Action> machineButtonsActions;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.warehouse.BackGroundPanel backGroundPanel1;
    private javax.swing.JButton conveyor;
    private javax.swing.JLabel conveyorLabel;
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
    private javax.swing.JLabel tittleLabel;
    // End of variables declaration//GEN-END:variables

}
