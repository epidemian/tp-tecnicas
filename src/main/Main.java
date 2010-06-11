package main;

import javax.swing.UIManager;

import model.game.Game;
import model.warehouse.Ground;
import view.MainFrame;
import view.ViewUtils;

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

		/*
		 * Main loop. Refresh 25 frames per second.
		 */
		/*
		 * TimerTask mainLoopTask = new TimerTask() {
		 * 
		 * @Override public void run() { mainFrame.repaint(); } }; Timer
		 * mainLoop = new Timer(); mainLoop.scheduleAtFixedRate(mainLoopTask, 0,
		 * 40);
		 */
	}
}
