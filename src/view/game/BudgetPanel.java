package view.game;

import static view.game.MoneyConstants.*;

public class BudgetPanel extends javax.swing.JPanel {

    /** Creates new form BudgetPanel */
    public BudgetPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        budgetBalanceLabel = new javax.swing.JLabel();

        budgetBalanceLabel.setFont(new java.awt.Font("Purisa", 0, 18)); // NOI18N
        budgetBalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        budgetBalanceLabel.setText("jLabel1");
        budgetBalanceLabel.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(budgetBalanceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(budgetBalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    public void setMoneyBalance(int balance){
        this.budgetBalanceLabel.setText(MONEY + ": " + MONEY_SYMBOL + " " + balance);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel budgetBalanceLabel;
    // End of variables declaration//GEN-END:variables

}
