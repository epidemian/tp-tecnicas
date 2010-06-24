package main;

import javax.swing.UIManager;

import model.utils.Config;
import model.utils.InputFactory;

import persistence.XMLConfig;
import persistence.XMLFactory;
import view.MainFrame;
import controller.MainController;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		setOSLookAndFeel();

		MainFrame mainFrame = new MainFrame();
		Config config = new XMLConfig();
		InputFactory factory = new XMLFactory(config);
		new MainController(mainFrame, factory, config);
	}

	private static void setOSLookAndFeel() {
		try {
			String laf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(laf);
		} catch (Exception e) {
			System.out.println("Cannot have OS look & feel");
		}
	}

}
