package main;

import javax.swing.UIManager;

import controller.MainController;

import view.MainFrame;


public class Main {

	public static void main(String[] args) throws InterruptedException {

		setOSLookAndFeel();

		MainFrame mainFrame = new MainFrame();
		new MainController(mainFrame);
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
