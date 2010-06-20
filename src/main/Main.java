package main;

import javax.swing.UIManager;

import persistence.InputFactory;
import persistence.XMLFactory;
import view.MainFrame;
import controller.MainController;


public class Main {

	public static void main(String[] args) throws InterruptedException {

		setOSLookAndFeel();

		MainFrame mainFrame = new MainFrame();
		
		InputFactory factory = new XMLFactory();
		new MainController(mainFrame, factory);
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
