package view.game;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class ResearchLabPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public ResearchLabPanel() {
        initComponents();

        this.researchingProgress.setMaximum(100);
        this.researchingProgress.setMinimum(0);
    }

    public JSpinner getDailyFundingSpinner(){
        return this.dailyFundingSpinner;
    }

    public JComboBox geTechnologyCombo(){
        return this.technologyCombo;
    }

    public JButton getResearchButton(){
        return this.researchButton;
    }

    public void setTechnologyPrice(int price){
        this.technologyPrice.setText(MoneyConstants.MONEY_SYMBOL + price);
    }

    public void setTechnologyInfo(String info){
        this.technologyInfo.setText(info);
    }

    public void setMaxDailyFunding(int max){
        this.dailyFundingLabel.setText(MAX_DAILY_FUNDING_PRE + max
                + MAX_DAILY_FUNDING_POST);
    }
    
    public void setResearchingTechnology(String tech){
        this.researchingTechnologyLabel.setText(RESEARCHING_TECH + tech);
    }

    public void setResearchProgress(int progress){
        this.researchingProgress.setValue(progress);
    }

    public void setTargetTechnology(String tech){
        this.targetLabel.setText(TARGET + tech);
    }

    private static final String MAX_DAILY_FUNDING_PRE = "Daily funding (max: ";
    private static final String MAX_DAILY_FUNDING_POST = "):";

    private static final String RESEARCHING_TECH = "Researching: ";
    private static final String TARGET = "Target: ";
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tittleLabel = new javax.swing.JLabel();
        dailyFundingLabel = new javax.swing.JLabel();
        technologiesResearchLabel = new javax.swing.JLabel();
        technologyCombo = new javax.swing.JComboBox();
        dailyFundingSpinner = new javax.swing.JSpinner();
        researchPanel = new javax.swing.JPanel();
        technologyInfoScrollPane = new javax.swing.JScrollPane();
        technologyInfo = new javax.swing.JTextArea();
        researchingTechnologyLabel = new javax.swing.JLabel();
        researchingProgress = new javax.swing.JProgressBar();
        targetLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        technologyPrice = new javax.swing.JLabel();
        researchButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(227, 539));
        setMinimumSize(new java.awt.Dimension(227, 539));
        setPreferredSize(new java.awt.Dimension(227, 539));

        tittleLabel.setFont(new java.awt.Font("Purisa", 0, 18));
        tittleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tittleLabel.setText("Research lab");
        tittleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        dailyFundingLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        dailyFundingLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dailyFundingLabel.setText("Daily Funding:");

        technologiesResearchLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        technologiesResearchLabel.setText("Technologies research:");

        technologyCombo.setFont(new java.awt.Font("Purisa", 0, 13));
        technologyCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        dailyFundingSpinner.setFont(new java.awt.Font("Purisa", 0, 13));

        researchPanel.setLayout(new java.awt.GridLayout(1, 2));

        technologyInfo.setColumns(20);
        technologyInfo.setFont(new java.awt.Font("Purisa", 0, 13));
        technologyInfo.setRows(5);
        technologyInfoScrollPane.setViewportView(technologyInfo);

        researchingTechnologyLabel.setFont(new java.awt.Font("Purisa", 0, 13));
        researchingTechnologyLabel.setText("Researching: ");

        targetLabel.setFont(new java.awt.Font("Purisa", 0, 13)); // NOI18N
        targetLabel.setText("Target:");

        technologyPrice.setFont(new java.awt.Font("Purisa", 0, 13));
        technologyPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        technologyPrice.setText("jLabel1");
        technologyPrice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        researchButton.setFont(new java.awt.Font("Purisa", 0, 13));
        researchButton.setText("Start");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(researchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(technologyPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(researchButton)
                    .addComponent(technologyPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(technologyCombo, 0, 205, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(technologiesResearchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dailyFundingSpinner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(dailyFundingLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(tittleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))))
                .addGap(11, 11, 11))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(technologyInfoScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(targetLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(researchingProgress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(researchingTechnologyLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addComponent(researchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dailyFundingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dailyFundingSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(technologiesResearchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(technologyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(researchingTechnologyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(researchingProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(targetLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(technologyInfoScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(researchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dailyFundingLabel;
    private javax.swing.JSpinner dailyFundingSpinner;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton researchButton;
    private javax.swing.JPanel researchPanel;
    private javax.swing.JProgressBar researchingProgress;
    private javax.swing.JLabel researchingTechnologyLabel;
    private javax.swing.JLabel targetLabel;
    private javax.swing.JLabel technologiesResearchLabel;
    private javax.swing.JComboBox technologyCombo;
    private javax.swing.JTextArea technologyInfo;
    private javax.swing.JScrollPane technologyInfoScrollPane;
    private javax.swing.JLabel technologyPrice;
    private javax.swing.JLabel tittleLabel;
    // End of variables declaration//GEN-END:variables

}
