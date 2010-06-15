package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.game.GamePanelController;

import model.game.Game;
import view.MainFrame;
import view.game.GamePanel;
import view.game.GroundPanel;
import view.game.GroundPanelContainer;

public class MainController {

	private Game game;
	private MainFrame mainFrame;

	public MainController(Game game, final MainFrame mainFrame) {
		this.game = game;
		this.mainFrame = mainFrame;

		setGamePanel();
                
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

	private void setMainFramePanel(JPanel panel) {
		panel.setOpaque(true);
		this.mainFrame.setContentPane(panel);
	}
}
