package view.warehouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.Painter;

public class GroundPainter implements Painter {

	/*
	 * TODO Do not hard-code tileSize =(.
	 */
	public static final int TILE_SIZE = 50;

	private Ground ground;

	public GroundPainter(Ground ground) {
		this.ground = ground;
	}

	@Override
	public void paint(Graphics graphics) {
		drawVerticalLines(graphics);
		dreawHorizontalLines(graphics);

		this.ground.visitElements(createElementPainter(graphics));
	}

	private void drawVerticalLines(Graphics graphics) {
		for (int col = 0; col <= this.ground.getCols(); col++) {

			int x1 = col * TILE_SIZE;
			int y1 = 0;
			int x2 = col * TILE_SIZE;
			int y2 = this.ground.getRows() * TILE_SIZE;

			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	private void dreawHorizontalLines(Graphics graphics) {
		for (int row = 0; row <= this.ground.getRows(); row++) {

			int x1 = 0;
			int y1 = row * TILE_SIZE;
			int x2 = this.ground.getCols() * TILE_SIZE;
			int y2 = row * TILE_SIZE;

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

					int x = element.getPosition().getCol() * TILE_SIZE;
					int y = element.getPosition().getRow() * TILE_SIZE;
					int width = element.getWidth() * TILE_SIZE;
					int height = element.getHeight() * TILE_SIZE;

					graphics.drawImage(image, x, y, width, height, null);
				}
			}
		};
	}

	public void paintResctangle(Graphics graphics, Position position,
			int width, int height, Color color) {
		graphics.setColor(color);
		int x = position.getCol() * TILE_SIZE;
		int y = position.getRow() * TILE_SIZE;
		int pixelW = width * TILE_SIZE;
		int pixelH = height * TILE_SIZE;
		graphics.fillRect(x, y, pixelW, pixelH);
	}
}
