package view.warehouse;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.game.Game;

import model.warehouse.Ground;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public MainFrame(Game game) {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Creates and sets up the content pane.
		 */
		this.contentPane = new GamePanel(game);
		this.contentPane.setOpaque(true);
		this.setContentPane(contentPane);

		// Display the window.
		this.setVisible(true);
		// this.pack();
		this.setSize(new Dimension(850, 650));
		//this.setLocationRelativeTo(null);
		this.maximize();
	}

	public void maximize() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

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
