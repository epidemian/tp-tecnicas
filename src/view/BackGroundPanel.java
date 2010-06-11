package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class BackGroundPanel extends JPanel {

	private BufferedImage image;

	public BackGroundPanel() {

	}

	public BackGroundPanel(BufferedImage image) {
		this.setImage(image);
	}

	public void setImage(BufferedImage image) {
		// Check not null.
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.image != null) {
			Dimension size = this.getSize();
			g.drawImage(this.image, 0, 0, size.width, size.height, this);
		}
	}
}
