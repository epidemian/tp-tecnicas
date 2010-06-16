package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import controller.game.GamePanelController;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.game.Game;
import view.MainFrame;
import view.game.GamePanel;
import view.game.GroundPanelContainer;
import view.game.MainPanel;

public class MainController {

	private Game game;
	private MainFrame mainFrame;

        private static final String[] difficultyLevels = { "Easy", "Normal", "Hard" };
        private static final int[] initialMoney = {10000,15000,20000};

	public MainController(Game game, final MainFrame mainFrame) {
		this.game = game;
		this.mainFrame = mainFrame;

		//setGamePanel();
                setMainPanel();

		mainFrame.setVisible(true);
		mainFrame.requestFocus();

		/*
		 * Main loop. Refresh 20 frames per second. TODO: Sacar main loop de
		 * acá...
		 */
		Timer mainLoopTimer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.repaint();
			}
		});
		mainLoopTimer.start();

		// Give focus to GamePanel when selected.
		mainFrame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				mainFrame.getContentPane().requestFocusInWindow();
			}
		});
	}

	private void setGamePanel() {

                GroundPanelContainer groundPanel
                    = new GroundPanelContainer(this.game.getGround());
		GamePanel gamePanel = new GamePanel(groundPanel);
		new GamePanelController(this.game, gamePanel);
                setMainFramePanel(gamePanel);
		
		// TODO
//		ToolBarPanel toolbar = gamePanel.getToolbar();
//		toolbar.addSellActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO
//				//game.sellGround();
//			}
//		});
		
	}

        private void setMainPanel(){
           MainPanel mainPanel = new MainPanel();

           final JComboBox difficultyCombo = mainPanel.getDifficultyCombo();
           final JTextField nameTextField = mainPanel.getPlayerNameTextArea();

           // Init combo.
           difficultyCombo.removeAllItems();
           for (int i = 0; i < difficultyLevels.length; i++) {
                difficultyCombo.addItem(difficultyLevels[i]);
           }

           mainPanel.addStartActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                 int selectedItem = difficultyCombo.getSelectedIndex();
                 int money = initialMoney[selectedItem];

                 String name = nameTextField.getText();
                 // TODO chequear que el nombre no sea vacio.

                 MainController.this.setGamePanel();
            }

            });

            this.setMainFramePanel(mainPanel);
        }

	private void setMainFramePanel(JPanel panel) {
		panel.setOpaque(true);
		this.mainFrame.setContentPane(panel);
	}
}
