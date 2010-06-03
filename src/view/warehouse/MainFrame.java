package view.warehouse;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.warehouse.Ground;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public MainFrame(Ground ground) {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Creates and sets up the content pane.
		 */
		this.contentPane = new GamePanel(ground);
		this.contentPane.setOpaque(true);
		this.setContentPane(contentPane);

		// Display the window.
		this.setVisible(true);
		// this.pack();
		this.setSize(new Dimension(640,480));
		this.maximize();
	}

	public void maximize() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	public static void main(String[] args) throws InterruptedException {

		Ground ground = ViewUtils.creatGroundSample1();
		
		final MainFrame mainFrame = new MainFrame(ground);
		mainFrame.setVisible(true);
		
		/*
		 * Main loop. Refresh 25 frames per second.
		 */
		TimerTask mainLoopTask = new TimerTask(){

			@Override
			public void run() {
				mainFrame.repaint();
			}			
		};
		
		Timer mainLoop = new Timer();
		mainLoop.scheduleAtFixedRate(mainLoopTask, 0, 40);
	}
}
