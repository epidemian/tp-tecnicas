package view.game;

import javax.swing.JScrollPane;

import model.warehouse.Ground;

public class GroundPanelContainer extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private GroundPanel groundPanel;

	private static int DEFAULT_TILE_SIZE = 50;
	
	public GroundPanelContainer(Ground ground, int tileSize){
		/*
		 * Specifies scroll-bars policies. The scrolls will be shown ALWAYS.
		 * They can also be specified with NEEDED OR NEVER.
		 */
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.groundPanel = new GroundPanel(ground,tileSize);
		this.setViewportView(this.groundPanel);
	}
	
	public GroundPanelContainer(Ground ground) {
		this(ground, DEFAULT_TILE_SIZE);

	}
	
	public GroundPanel getGroundPanel() {
		return groundPanel;
	}

}