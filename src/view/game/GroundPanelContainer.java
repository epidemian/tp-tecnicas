package view.game;

import javax.swing.JScrollPane;

import model.warehouse.Ground;

public class GroundPanelContainer extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private GroundPanel groundPanel;
	
	public GroundPanelContainer(Ground ground, int tileSize){
		this(new GroundPanel(ground,tileSize));
	}
	
	public GroundPanelContainer(Ground ground) {
		this(new GroundPanel(ground));
	}
	
	private GroundPanelContainer(GroundPanel groundPanel) {
		/*
		 * Specifies scroll-bars policies. The scrolls will be shown ALWAYS.
		 * They can also be specified with NEEDED OR NEVER.
		 */
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.groundPanel = groundPanel;
		this.setViewportView(groundPanel);
	}
	
	public GroundPanel getGroundPanel() {
		return groundPanel;
	}

}