package view.warehouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.warehouse.Ground;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.warehouse.edition.EditionToolMediator;

public class GroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Ground ground;

	/*
	 * TODO Do not hard-code tileSize =(.
	 */
	private int tileSize = 50;

	public GroundPanel(Ground ground) {
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

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		drawVerticalLines(graphics);
		dreawHorizontalLines(graphics);

		this.ground.visitElements(createElementPainter(graphics));
	}

	private void drawVerticalLines(Graphics graphics) {
		for (int col = 0; col <= this.ground.getCols(); col++) {

			int x1 = col * this.tileSize;
			int y1 = 0;
			int x2 = col * this.tileSize;
			int y2 = this.ground.getRows() * this.tileSize;

			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	private void dreawHorizontalLines(Graphics graphics) {
		for (int row = 0; row <= this.ground.getRows(); row++) {

			int x1 = 0;
			int y1 = row * this.tileSize;
			int x2 = this.ground.getCols() * this.tileSize;
			int y2 = row * this.tileSize;

			graphics.drawLine(x1, y1, x2, y2);
		}
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

	public void setPainter(EditionToolMediator toolMediator) {
		// TODO Auto-generated method stub
		
	}
}