package view.warehouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.warehouse.Ground;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;

public class GroundPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public GroundPanel(Ground ground) {

		/*
		 * Specifies scroll-bars policies. The scrolls will be shown ALWAYS.
		 * They can also be specified with NEEDED OR NEVER.
		 */
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.setViewportView(new DrawingPanel(ground));
	}

	/*
	 * The component inside the scroll pane.
	 */
	private class DrawingPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private Ground ground;

		/*
		 * TODO Do not hard-code tileSize =(.
		 */
		private int tileSize = 75;

		public DrawingPanel(Ground ground) {
			this.ground = ground;
			/*
			 * TODO Do not hard-code Color.white =(.
			 */
			this.setBackground(Color.white);
			/*
			 * This a scroll-able panel. The scroll can be used in the area.
			 */
			setPreferredSize(this.getGroundSize());
		}

		private Dimension getGroundSize() {
			return new Dimension(this.ground.getCols() * this.tileSize,
					this.ground.getRows() * this.tileSize);
		}

		protected void paintComponent(Graphics graphics) {

			super.paintComponent(graphics);

			Dimension groundSizeInTiles = new Dimension(this.ground.getCols(),
					this.ground.getRows());

			// Vertical lines.
			for (int col = 0; col <= groundSizeInTiles.width; col++) {

				int x1 = col * this.tileSize;
				int y1 = 0;
				int x2 = col * this.tileSize;
				int y2 = groundSizeInTiles.height * this.tileSize;

				graphics.drawLine(x1, y1, x2, y2);
			}

			// Horizontal lines.
			for (int row = 0; row <= groundSizeInTiles.height; row++) {

				int x1 = 0;
				int y1 = row * this.tileSize;
				int x2 = groundSizeInTiles.width * this.tileSize;
				int y2 = row * this.tileSize;

				graphics.drawLine(x1, y1, x2, y2);
			}

			this.ground.visitElements(createElementPainter(graphics));
		}

		/*
		 * Visitor used to draw each element in the warehouse.
		 */
		private TileElementVisitor createElementPainter(final Graphics graphics) {
			return new TileElementImageRecognizer() {
				private List<TileElement> bigTouchedTiles = new ArrayList<TileElement>();
				
				@Override
				protected void onTileElmentVisited(TileElement element,
						BufferedImage image) {
					/*
					 * Checks if 'element' is contained in the touchedTiles list. If
					 * it is not, adds the element to the list (if size is bigger
					 * than one tile, because is the only way that can try to draw
					 * it twice.)
					 */
					if (!this.bigTouchedTiles.contains(element)) {
						if (element.getWidth() > 1 || element.getHeight() > 1)
							this.bigTouchedTiles.add(element);

						int x = element.getPosition().getCol() * tileSize;
						int y = element.getPosition().getRow() * tileSize;
						int width = element.getWidth() * tileSize;
						int height = element.getHeight() * tileSize;

						graphics.drawImage(image, x, y, width, height, null);
					}
				}
			};
		}
	}
}