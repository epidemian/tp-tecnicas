package view.game;

import javax.swing.JScrollPane;

public class GroundPanelContainer extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private GroundPanel groundPanel;
	
	public GroundPanelContainer() {
		/*
		 * Specifies scroll-bars policies. The scrolls will be shown ALWAYS.
		 * They can also be specified with NEEDED OR NEVER.
		 */
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	}
	
	public GroundPanelContainer(GroundPanel groundPanel) {
		this();
		setGroundPanel(groundPanel);
	}
	
	public GroundPanel getGroundPanel() {
		return groundPanel;
	}

	public void setGroundPanel(GroundPanel groundPanel) {
		this.groundPanel = groundPanel;
		this.setViewportView(groundPanel);
	}
}