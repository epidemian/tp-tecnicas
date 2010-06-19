package controller.game;

import controller.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import model.exception.BusinessLogicException;
import model.game.Player;
import model.warehouse.Ground;
import view.game.GroundPanelContainer;
import view.game.GroundSelectionPanel;

public class GroundSelectionPanelController {

    private static final String GROUND_PREFIX = "Ground ";

    public GroundSelectionPanelController(GroundSelectionPanel selectionPanel
            , Player player, MainController mainController){

        List<Ground> grounds = null;

        try {
            grounds =  mainController.getInputFactory().loadGrounds();
        } catch (Exception e) {
                throw new RuntimeException(e);
        }

        if (grounds.isEmpty())
            throw new BusinessLogicException("Empty grounds list");

        this.initBuyComboFromGroundSelectionPanel(grounds, selectionPanel);
	this.initGroundSelectionPanelButtons(selectionPanel, player, mainController);

        int balance = player.getBudget().getBalance();
	selectionPanel.getBudgetPanel().setMoneyBalance(balance);
    }

    	private void initBuyComboFromGroundSelectionPanel(
			final List<Ground> grounds,
			final GroundSelectionPanel selectionPanel) {

            final JComboBox buyCombo = selectionPanel.getGroundCombo();
            buyCombo.removeAllItems();

            // Adds grounds names to the combo.
            for (int i = 0; i < grounds.size(); i++)
                    buyCombo.addItem(GROUND_PREFIX + i + 1);

            this.buyComboGroundSelectionAction(buyCombo, grounds, selectionPanel);

            // Buy buttons action listener.
            buyCombo.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent ie) {
                            buyComboGroundSelectionAction(buyCombo,
                                            grounds, selectionPanel);
                    }
            });
	}

	private void buyComboGroundSelectionAction(final JComboBox buyCombo,
			final List<Ground> grounds,
			final GroundSelectionPanel selectionPanel) {

            // Gets ground selected.
            int comboIndex = buyCombo.getSelectedIndex();
            Ground ground = grounds.get(comboIndex);

            // Set ground in ground panel container and show prices.
            GroundPanelContainer groundPanelContainer = new GroundPanelContainer(
                            ground);

            selectionPanel.setGroundPanelContainer(groundPanelContainer);
            selectionPanel.setPurchasePrice(ground.getPurchasePrice());
            selectionPanel.setRentPrice(ground.getRentPrice());
	}

	private void initGroundSelectionPanelButtons(final GroundSelectionPanel
            selectionPanel, final Player player, final MainController mainControler) {

            selectionPanel.addBuyButtonActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {

                            Ground ground = selectionPanel.getGroundPanelContainer()
                                            .getGroundPanel().getGround();
                            player.purchaseWarehouse(ground);
                            mainControler.setGamePanel(player);
                    }
            });

            selectionPanel.addRentButtonActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {

                            Ground ground = selectionPanel.getGroundPanelContainer()
                                            .getGroundPanel().getGround();
                            player.rentWarehouse(ground);
                            mainControler.setGamePanel(player);
                    }
            });

            selectionPanel.addBackButtonActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                            mainControler.setMainPanel();
                    }
            });
	}
}
