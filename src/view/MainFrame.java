package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import view.game.GamePanel;

import model.game.Game;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		this.setVisible(true);
		// this.pack();
		this.setSize(new Dimension(850, 650));
		// this.setLocationRelativeTo(null);
		this.maximize();

	}

	public void maximize() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

}
