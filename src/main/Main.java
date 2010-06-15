package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import javax.swing.UIManager;

import controller.MainController;

import model.game.Game;
import model.warehouse.Ground;
import view.MainFrame;
import view.game.ViewUtils;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		setOSLookAndFeel();

		Game game = createModel();
		MainFrame mainFrame = new MainFrame();
		new MainController(game, mainFrame);
	}

	private static void setOSLookAndFeel() {
		try {
			String laf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(laf);
		} catch (Exception e) {
			System.out.println("Cannot have OS look & feel");
		}
	}

	private static Game createModel() {
		Ground ground = ViewUtils.creatGroundSample1();
		Game game = new Game(ground);
		return game;
	}
}
