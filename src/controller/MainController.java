package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.game.GamePanelController;
import controller.game.GroundSelectionPanelController;
import controller.game.MainPanelController;

import java.awt.Dimension;

import persistence.InputFactory;

import model.game.Player;
import view.MainFrame;
import view.game.GamePanel;
import view.game.GroundPanelContainer;
import view.game.GroundSelectionPanel;
import view.game.MainPanel;

public class MainController {

	private MainFrame mainFrame;

	private InputFactory inputFactory;

	private static final Dimension MAIN_PANEL_SIZE = new Dimension(640, 480);
	private static final Dimension GROUND_SELECTION_PANEL_SIZE = new Dimension(
			320, 670);
	protected static final float WIN_VALUE = 50000;

	public MainController(final MainFrame mainFrame, InputFactory inputFactory) {
		this.mainFrame = mainFrame;
		this.inputFactory = inputFactory;

		setMainPanel();

		mainFrame.setVisible(true);
		mainFrame.requestFocus();

		// Give focus to GamePanel when selected.
		// TODO Delete this if doesn't work!
		mainFrame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				mainFrame.getContentPane().requestFocusInWindow();
			}
		});
	}

        public InputFactory getInputFactory(){
            return this.inputFactory;
        }

        public JFrame getMainFrame(){
            return this.mainFrame;
        }

	public void setGamePanel(Player game) {

            GroundPanelContainer groundPanel = new GroundPanelContainer(game
                            .getGround());
            final GamePanel gamePanel = new GamePanel(groundPanel);

            this.mainFrame.setResizable(true);
            this.mainFrame.maximize();
            this.setMainFramePanel(gamePanel);

            GamePanelController gamePanelController = new GamePanelController(game,
                            gamePanel, this);

            this.mainFrame.addContainerListener(gamePanelController
                            .getGamePanelRemovedListener());
	}

	public void setMainPanel() {

             MainPanel mainPanel = new MainPanel();
             new MainPanelController(mainPanel,this);

            this.mainFrame.setResizable(false);
            this.mainFrame.setSize(MAIN_PANEL_SIZE);
            this.mainFrame.setLocationRelativeTo(null);
            this.setMainFramePanel(mainPanel);
	}

	public void setGroundSelectionPanel(Player player) {

            GroundSelectionPanel selectionPanel = new GroundSelectionPanel();
            new GroundSelectionPanelController(selectionPanel, player, this);

            // Frame configuration.
            this.mainFrame.setResizable(true);
            this.mainFrame.setSize(GROUND_SELECTION_PANEL_SIZE);
            this.mainFrame.setLocationRelativeTo(null);
            this.setMainFramePanel(selectionPanel);
        }

	private void setMainFramePanel(JPanel panel) {
		panel.setOpaque(true);
		this.mainFrame.setContentPane(panel);
		this.mainFrame.invalidate();
		this.mainFrame.validate();
		this.mainFrame.repaint();
	}
}
