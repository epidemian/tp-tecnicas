package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;

import model.exception.BusinessLogicException;
import model.game.Player;
import model.warehouse.Ground;
import view.game.GroundSelectionPanel;
import view.game.ground.StaticGroundPanel;
import controller.MainController;

public class GroundSelectionPanelController {

	private static final String GROUND_PREFIX = "Ground ";
	private static final int TILE_SIZE = 20;

	private GroundSelectionPanel selectionPanel;
	private Player player;
	private List<Ground> grounds;

	public GroundSelectionPanelController(GroundSelectionPanel selectionPanel,
			Player player, MainController mainController) {

		this.selectionPanel = selectionPanel;
		this.player = player;

		try {
			this.grounds = mainController.getInputFactory().loadGrounds();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (this.grounds.isEmpty())
			throw new BusinessLogicException("Empty grounds list");

		this.initBuyComboFromGroundSelectionPanel();
		this.initGroundSelectionPanelButtons(mainController);

		int balance = player.getBudget().getBalance();
		selectionPanel.getBudgetPanel().setMoneyBalance(balance);
	}

	private void initBuyComboFromGroundSelectionPanel() {

		final JComboBox buyCombo = selectionPanel.getGroundCombo();
		buyCombo.removeAllItems();

		// Adds grounds names to the combo.
		for (int i = 1; i < grounds.size() + 1; i++)
			buyCombo.addItem(GROUND_PREFIX + i);
				
		this.buyComboGroundSelectionAction(buyCombo, grounds, selectionPanel);

		// Buy buttons action listener.
		buyCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ie) {
				buyComboGroundSelectionAction(buyCombo, grounds, selectionPanel);
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
		StaticGroundPanel groundPanel = new StaticGroundPanel(ground, TILE_SIZE);

		selectionPanel.setGroundPanel(groundPanel);
		selectionPanel.setPurchasePrice(ground.getPurchasePrice());
		selectionPanel.setRentPrice(ground.getRentPrice());
		
		// Enable-Disable buy-rent buttons.
		int balance = this.player.getBudget().getBalance();
		
		selectionPanel.setBuyButtonEnabled(ground.getPurchasePrice() < balance);
		selectionPanel.setRentButtonEnabled(ground.getRentPrice() < balance);
	}

	private void initGroundSelectionPanelButtons(
			final MainController mainControler) {

		selectionPanel.addBuyButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				Ground ground = selectionPanel.getGroundPanel().getGround();
				player.purchaseWarehouse(ground);
				mainControler.setGamePanel(player);
			}
		});

		selectionPanel.addRentButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				Ground ground = selectionPanel.getGroundPanel().getGround();
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
