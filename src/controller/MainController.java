package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.game.GamePanelController;
import controller.game.LineElementsMarketPanelController;
import controller.game.RawMaterialsMarketPanelController;

import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.exception.BusinessLogicException;
import model.game.Game;
import model.warehouse.Ground;
import view.MainFrame;
import view.game.GamePanel;
import view.game.GroundPanelContainer;
import view.game.GroundSelectionPanel;
import view.game.LineElementsMarketPanel;
import view.game.MainPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ToolBarPanel;
import view.game.edition.EditionActions;
import view.game.edition.KeyInputActionMapper;

public class MainController {

	private Game game;
	private MainFrame mainFrame;

	private static final String GROUND_PREFIX = "Ground ";
	private static final String[] DIFFICULTY_LEVELS = { "Easy", "Normal",
			"Hard" };
	private static final int[] INITIAL_MONEY = { 10000, 15000, 20000 };
	private static final float RENT_FACTOR = 0.01f;

	private static final Dimension DIALOG_SIZE = new Dimension(350, 150);
	private static final Dimension MAIN_PANEL_SIZE = new Dimension(640, 480);
	private static final Dimension GROUND_SELECTION_PANEL_SIZE = new Dimension(
			320, 670);

	public MainController(Game game, final MainFrame mainFrame) {
		this.game = game;
		this.mainFrame = mainFrame;

		// setGamePanel();
		// setMainPanel();
		setGroundSelectionPanel();

		mainFrame.setVisible(true);
		mainFrame.requestFocus();

		// Give focus to GamePanel when selected.
		mainFrame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				mainFrame.getContentPane().requestFocusInWindow();
			}
		});
	}

	private void setGamePanel() {

		GroundPanelContainer groundPanel = new GroundPanelContainer(this.game
				.getGround());
		final GamePanel gamePanel = new GamePanel(groundPanel);

		this.mainFrame.setResizable(true);
		this.mainFrame.maximize();
		this.setMainFramePanel(gamePanel);

		GamePanelController gamePanelController = new GamePanelController(
				this.game, gamePanel, this);
		System.out.println("is enabled " + this.mainFrame.isEnabled());
		this.mainFrame.addContainerListener(gamePanelController
				.getGamePanelRemovedListener());
	}

	private void setMainPanel() {
		MainPanel mainPanel = new MainPanel();

		final JComboBox difficultyCombo = mainPanel.getDifficultyCombo();
		final JTextField nameTextField = mainPanel.getPlayerNameTextArea();

		// Init combo.
		difficultyCombo.removeAllItems();
		for (int i = 0; i < DIFFICULTY_LEVELS.length; i++) {
			difficultyCombo.addItem(DIFFICULTY_LEVELS[i]);
		}

		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				int selectedItem = difficultyCombo.getSelectedIndex();
				int money = INITIAL_MONEY[selectedItem];
				String name = nameTextField.getText();

				if (!name.isEmpty()) {
					MainController.this.game.setInitialMoney(money);
					MainController.this.game.setPlayerName(name);
					MainController.this.setGroundSelectionPanel();
				} else
					JOptionPane.showMessageDialog(
							MainController.this.mainFrame,
							"Please insert you name", "Empty name",
							JOptionPane.ERROR_MESSAGE);
			}
		};

		mainPanel.addStartActionListener(al);
		nameTextField.addActionListener(al);

		this.mainFrame.setResizable(false);
		this.mainFrame.setSize(MAIN_PANEL_SIZE);
		this.mainFrame.setLocationRelativeTo(null);
		this.setMainFramePanel(mainPanel);
	}

	public void setGroundSelectionPanel() {

		final List<Ground> grounds = this.game.getGrounds();

		if (grounds.isEmpty())
			throw new BusinessLogicException("Empty grounds list");

		GroundSelectionPanel selectionPanel = new GroundSelectionPanel();

		this.initBuyComboFromGroundSelectionPanel(grounds, selectionPanel);
		this.initGroundSelectionPanelButtons(selectionPanel);

		int balance = this.game.getBudget().getBalance();
		selectionPanel.getBudgetPanel().setMoneyBalance(balance);

		// Frame configuration.
		this.mainFrame.setResizable(true);
		this.mainFrame.setSize(GROUND_SELECTION_PANEL_SIZE);
		this.mainFrame.setLocationRelativeTo(null);
		this.setMainFramePanel(selectionPanel);
	}

	private void initBuyComboFromGroundSelectionPanel(
			final List<Ground> grounds,
			final GroundSelectionPanel selectionPanel) {

		final JComboBox buyCombo = selectionPanel.getGroundCombo();
		buyCombo.removeAllItems();

		// Adds grounds names to the combo.
		for (int i = 1; i < grounds.size(); i++)
			buyCombo.addItem(GROUND_PREFIX + i);

		this.buyComboGroundSelectionAction(buyCombo, grounds, selectionPanel);

		// Buy buttons action listener.
		buyCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ie) {
				MainController.this.buyComboGroundSelectionAction(buyCombo,
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
		MainController.this.game.setGround(ground);

		selectionPanel.setGroundPanelContainer(groundPanelContainer);
		selectionPanel.setPurchasePrice(ground.getPrice());
		selectionPanel.setRentPrice((int) (ground.getPrice() * RENT_FACTOR));
	}

	private void initGroundSelectionPanelButtons(
			final GroundSelectionPanel selectionPanel) {

		selectionPanel.addBuyButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				Ground ground = selectionPanel.getGroundPanelContainer()
						.getGroundPanel().getGround();
				// Crear Purchase Warehouse!
				MainController.this.game.setGround(ground);
				MainController.this.game.getBudget().decrement(
						ground.getPrice());
				MainController.this.setGamePanel();
			}
		});

		selectionPanel.addRentButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				Ground ground = selectionPanel.getGroundPanelContainer()
						.getGroundPanel().getGround();
				// Crear Rent Warehouse!
				MainController.this.game.setGround(ground);
				MainController.this.game.getBudget().decrement(
						(int) (ground.getPrice() * RENT_FACTOR));
				MainController.this.setGamePanel();
			}
		});

		selectionPanel.addBackButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				MainController.this.setMainPanel();
			}
		});
	}

	private void setMainFramePanel(JPanel panel) {
		panel.setOpaque(true);
		this.mainFrame.setContentPane(panel);
		this.mainFrame.invalidate();
		this.mainFrame.validate();
		this.mainFrame.repaint();
	}

	public static boolean showDialog(String title, String message) {
		final JDialog dialog = new JDialog((JFrame) null, title, true);
		final JOptionPane optionPane = new JOptionPane(message,
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();

				if (dialog.isVisible() && (e.getSource() == optionPane)
						&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
					dialog.setVisible(false);
				}
			}
		});

		dialog.setContentPane(optionPane);
		dialog.setSize(DIALOG_SIZE);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		return ((Integer) optionPane.getValue()).intValue() == JOptionPane.YES_OPTION;
	}
}
