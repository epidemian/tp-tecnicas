package view.warehouse.edition;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class EditionToolbar extends JPanel {

	private KeyListener keyListener;

	public EditionToolbar(EditionTool toolMediator) {
		keyListener = new Listener();
	}

	public KeyListener getKeyListener() {
		return this.keyListener;
	}

	private class Listener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Key pressed: " + e);
		}

	}
}
