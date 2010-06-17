package view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.production.Direction;
import model.production.elements.Conveyor;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;
import view.Painter;

public class GroundPainter implements Painter {

	private static final Color HIGHLIGHT_COLOR = new Color(0, 1, 0, 0.3F);
	private static final Color HIGHLIGHT_ARROWS_COLOR = new Color(0, 1, 0, 0.7F);

	private final Ground ground;
	private final int tileSize;

	public GroundPainter(Ground ground, int tileSize) {
		this.ground = ground;
		this.tileSize = tileSize;
	}
	
	public int getTileSize() {
		return tileSize;
	}

	@Override
	public void paint(Graphics2D graphics) {
		drawVerticalLines(graphics);
		drawHorizontalLines(graphics);

		this.ground.visitElements(createElementPainter(graphics));
	}

	private void drawVerticalLines(Graphics2D graphics) {
		for (int col = 0; col <= this.ground.getCols(); col++) {

			int x1 = col * this.tileSize;
			int y1 = 0;
			int x2 = col * this.tileSize;
			int y2 = this.ground.getRows() * this.tileSize;

			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	private void drawHorizontalLines(Graphics2D graphics) {
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
	private TileElementVisitor createElementPainter(final Graphics2D graphics) {
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

	public void drawRectangle(Graphics2D graphics, Position position,
			int width, int height, Color color) {
		int x = position.getCol() * this.tileSize;
		int y = position.getRow() * this.tileSize;
		int pixelW = width * this.tileSize;
		int pixelH = height * this.tileSize;

		graphics.setColor(color);
		graphics.fillRect(x, y, pixelW, pixelH);
	}

	public void highlightTileElement(final TileElement tileElement,
			final Graphics2D graphics) {
		tileElement.accept(new TileElementVisitor() {
			@Override
			public void visitConveyor(Conveyor conveyor) {
				visitProductionLineElement(conveyor);
			}

			@Override
			public void visitProductionMachine(ProductionMachine machine) {
				visitProductionLineElement(machine);
			}

			@Override
			public void visitQualityControlMachine(QualityControlMachine machine) {
				visitProductionLineElement(machine);
			}

			@Override
			public void visitWall(Wall wall) {
				drawHighlightRectangle();
			}

			private void visitProductionLineElement(
					ProductionLineElement lineElement) {
				drawHighlightRectangle();
				drawProductionLineElementArrows(lineElement, graphics);
			}

			private void drawHighlightRectangle() {
				drawRectangle(graphics, tileElement.getPosition(), tileElement
						.getWidth(), tileElement.getHeight(), HIGHLIGHT_COLOR);
			}
		});
	}
	
	public void drawProductionLineElementArrows(
			ProductionLineElement lineElement, Graphics2D graphics) {
		drawProductionLineElementArrows(lineElement, graphics, HIGHLIGHT_ARROWS_COLOR);
	}

	public void drawProductionLineElementArrows(
			ProductionLineElement lineElement, Graphics2D graphics, Color color) {
		drawInputArrow(lineElement.getInputConnectionPosition(), lineElement
				.getInputConnectionDirection(), color, graphics);
		drawOutputArrow(lineElement.getOutputConnectionPosition(), lineElement
				.getOutputConnectionDirection(), color, graphics);
	}

	public void drawInputArrow(Position position, Direction direction,
			Color color, Graphics2D graphics) {
		double[] xFloats = { 0.1, 0.5, 0.9 };
		double[] yFloats = { 0.7, 0.9, 0.7 };
		int[] xInts = scaleToInts(this.tileSize, xFloats);
		int[] yInts = scaleToInts(this.tileSize, yFloats);
		Polygon arrow = new Polygon(xInts, yInts, xFloats.length);
		drawArrow(arrow, position, direction, color, graphics);
	}

	public void drawOutputArrow(Position position, Direction direction,
			Color color, Graphics2D graphics) {
		double[] xFloats = { 0.1, 0.5, 0.9 };
		double[] yFloats = { 0.9, 0.7, 0.9 };
		int[] xInts = scaleToInts(this.tileSize, xFloats);
		int[] yInts = scaleToInts(this.tileSize, yFloats);
		Polygon arrow = new Polygon(xInts, yInts, xFloats.length);
		drawArrow(arrow, position, direction, color, graphics);
	}

	private int[] scaleToInts(double scale, double[] floats) {
		int[] ints = new int[floats.length];
		for (int i = 0; i < floats.length; i++)
			ints[i] = (int) (floats[i] * scale);
		return ints;
	}

	private void drawArrow(Polygon arrow, Position position,
			Direction direction, Color color, Graphics2D graphics) {
		AffineTransform backupTransform = graphics.getTransform();
		double theta = direction.getAssociatedRotation();
		double tx = this.tileSize * position.getCol();
		double ty = this.tileSize * position.getRow();
		graphics.translate(tx, ty);
		graphics.rotate(theta, this.tileSize * 0.5, this.tileSize * 0.5);
		graphics.setColor(color);
		graphics.fillPolygon(arrow);

		graphics.setTransform(backupTransform);
	}
}
