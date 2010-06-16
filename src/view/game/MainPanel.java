package view.game;

import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class MainPanel extends javax.swing.JPanel {

    private static final String BACK_GROUND = "mainPanelBackGround.png";

    /** Creates new form MainPanel */
    public MainPanel() {
        initComponents();
        this.backGroundPanel1.setImage(ImageLoader.getImage(BACK_GROUND));
    }

    public JComboBox getDifficultyCombo(){
        return this.difficultyCombo;
    }

    public JTextField getPlayerNameTextArea(){
        return this.playerNameTextArea;
    }

    public void addStartActionListener(ActionListener al){
        this.startButton.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backGroundPanel1 = new view.game.BackGroundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        difficultyCombo = new javax.swing.JComboBox();
        playerNameTextArea = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();

        jLabel1.setBackground(java.awt.Color.darkGray);
        jLabel1.setFont(new java.awt.Font("Purisa", 0, 15));
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Player name:");
        jLabel1.setBorder(new javax.swing.border.LineBorder(java.awt.Color.gray, 3, true));
        jLabel1.setOpaque(true);

        jLabel2.setBackground(java.awt.Color.darkGray);
        jLabel2.setFont(new java.awt.Font("Purisa", 0, 15));
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Difficulty:");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray, 3));
        jLabel2.setOpaque(true);

        difficultyCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        startButton.setFont(new java.awt.Font("Purisa", 0, 18));
        startButton.setText("Start");

        javax.swing.GroupLayout backGroundPanel1Layout = new javax.swing.GroupLayout(backGroundPanel1);
        backGroundPanel1.setLayout(backGroundPanel1Layout);
        backGroundPanel1Layout.setHorizontalGroup(
            backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backGroundPanel1Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playerNameTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                    .addGroup(backGroundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(difficultyCombo, 0, 148, Short.MAX_VALUE))
                    .addGroup(backGroundPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                        .addGap(93, 93, 93)))
                .addGap(165, 165, 165))
        );
        backGroundPanel1Layout.setVerticalGroup(
            backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanel1Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerNameTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(backGroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(difficultyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(131, 131, 131))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.game.BackGroundPanel backGroundPanel1;
    private javax.swing.JComboBox difficultyCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField playerNameTextArea;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables

}
