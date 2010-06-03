package view.warehouse;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.warehouse.Ground;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/*
	 * TODO There will be more elements in this panel, for example, some buttons
	 * to choose between the machines that are available and insert them in the
	 * ground.
	 */
	private JScrollPane groundPanel;

	/*
	 * Represents the state of the game. It can be CONSTRUCTION_MODE or
	 * PLAYING_MODE.
	 */
	// private GameState gameState;

	public GamePanel(Ground ground) {
		/*
		 * A border layout lays out a container, arranging and resizing its
		 * components to fit in five regions: north, south, east, west, and
		 * center.
		 */
		super(new BorderLayout());
		
		/*
		 * Sets up the ground panel.
		 */
		this.groundPanel = new GroundPanel(ground);

		/*
		 * Preferred size of the Ground Panel. TODO Do not hard-code this value.
		 * It can be parameterizes with a configuration file.
		 */
		// this.setPreferredSize(new Dimension(100, 100));

		/*
		 * Adds the groundPanel to the JPanel.
		 */
		add(this.groundPanel, BorderLayout.CENTER);
	}
}
