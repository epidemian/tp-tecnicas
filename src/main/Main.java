package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import javax.swing.UIManager;

import model.game.Game;
import model.warehouse.Ground;
import view.warehouse.MainFrame;
import view.warehouse.ViewUtils;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		try {
			String laf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(laf);
		} catch (Exception e) {
			System.out.println("Cannot look like operation system");
		}

		Ground ground = ViewUtils.creatGroundSample1();
		Game game = new Game(ground);
		final MainFrame mainFrame = new MainFrame(game);
		mainFrame.setVisible(true);
		mainFrame.requestFocus();

		/*
		 * Main loop. Refresh 20 frames per second.
		 */
		Timer mainLoopTimer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.repaint();
			}
		});
		mainLoopTimer.start();
		/*
		 * TimerTask mainLoopTask = new TimerTask() {
		 * 
		 * @Override public void run() { mainFrame.repaint(); } }; Timer
		 * mainLoop = new Timer(); mainLoop.scheduleAtFixedRate(mainLoopTask, 0,
		 * 40);
		 */
	}
}
