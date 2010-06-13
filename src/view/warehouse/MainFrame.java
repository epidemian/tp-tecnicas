package view.warehouse;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.game.Game;

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
		// this.setLocationRelativeTo(null);
		this.maximize();
		
		// Give focus to GamePanel when selected.
		this.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				MainFrame.this.contentPane.requestFocusInWindow();
			}
		});
	}

	public void maximize() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

}
