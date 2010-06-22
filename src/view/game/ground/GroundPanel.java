package view.game.ground;

import static model.utils.ArgumentUtils.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Path2D.Double;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;

import javax.swing.JPanel;

import model.production.Direction;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;
import view.Painter;

public class GroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Color HIGHLIGHT_COLOR = new Color(0, 1, 0, 0.3F);
	private static final Color HIGHLIGHT_ARROWS_COLOR = new Color(0, 1, 0, 0.7F);
	private static final Color NOTIFICATION_COLOR = Color.BLACK;

	private static final int DEFAULT_TILE_SIZE = 50;

	private static final float NOTIFICATION_TEXT_SIZE = 15f;

	private final Ground ground;
	private int tileSize;
	private Painter painter;

	private double elapsedTickTime;

	public GroundPanel(Ground ground) {
		this(ground, DEFAULT_TILE_SIZE);
	}

	public GroundPanel(Ground ground, int tileSize) {
		super();

		this.ground = ground;
		this.tileSize = tileSize;

		/*
		 * TODO Do not hard-code Color.white =(.
		 */
		this.setBackground(Color.WHITE);
		/*
		 * This a scroll-able panel. The scroll can be used in the area.
		 */
		int preferredWidth = ground.getCols() * tileSize;
		int preferredHeight = ground.getRows() * tileSize;
		this.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

		this.setPainter(new Painter() {
			@Override
			public void paint(Graphics2D graphics) {
			}
		});
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.scale(tileSize, tileSize);

		g2d.setStroke(new BasicStroke(1.0f / this.tileSize));
		drawVerticalLines(g2d);
		drawHorizontalLines(g2d);
		this.ground.visitElements(new TileElementPainter(this, g2d,
				this.elapsedTickTime));

		this.painter.paint(g2d);
	}

	private void drawVerticalLines(Graphics2D graphics) {
		for (int col = 0; col <= this.ground.getCols(); col++)
			graphics.drawLine(col, 0, col, this.ground.getRows());
	}

	private void drawHorizontalLines(Graphics2D graphics) {
		for (int row = 0; row <= this.ground.getRows(); row++)
			graphics.drawLine(0, row, this.ground.getCols(), row);
	}

	public void drawImage(Image img, int row, int col, int width, int height,
			Graphics2D graphics) {
		graphics.drawImage(img, col, row, width, height, null);
	}

	public void drawRectangle(Graphics2D graphics, Position position,
			int width, int height, Color color) {
		graphics.setColor(color);
		graphics.fillRect(position.getCol(), position.getRow(), width, height);
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

			@Override
			public void visitInputProductionLineElement(
					InputProductionLineElement inputLineElement) {
				visitProductionLineElement(inputLineElement);
			}

			@Override
			public void visitOutputProductionLineElement(
					OutputProductionLineElement outputLineElement) {
				visitProductionLineElement(outputLineElement);
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
		drawProductionLineElementArrows(lineElement, graphics,
				HIGHLIGHT_ARROWS_COLOR);
	}

	public void drawProductionLineElementArrows(
			ProductionLineElement lineElement, Graphics2D graphics, Color color) {
		if (lineElement.canHavePreviousLineElement())
			drawInputArrow(lineElement.getInputConnectionPosition(),
					lineElement.getInputConnectionDirection(), color, graphics);
		if (lineElement.canHaveNextLineElement())
			drawOutputArrow(lineElement.getOutputConnectionPosition(),
					lineElement.getOutputConnectionDirection(), color, graphics);
	}

	public void drawInputArrow(Position position, Direction direction,
			Color color, Graphics2D graphics) {
		Path2D.Double arrow = new Path2D.Double();
		arrow.moveTo(0.1, 0.7);
		arrow.lineTo(0.5, 0.9);
		arrow.lineTo(0.9, 0.7);

		drawArrow(arrow, position, direction, color, graphics);
	}

	public void drawOutputArrow(Position position, Direction direction,
			Color color, Graphics2D graphics) {
		Path2D.Double arrow = new Path2D.Double();
		arrow.moveTo(0.1, 0.9);
		arrow.lineTo(0.5, 0.7);
		arrow.lineTo(0.9, 0.9);
		drawArrow(arrow, position, direction, color, graphics);
	}

	private void drawArrow(Shape arrow, Position position, Direction direction,
			Color color, Graphics2D graphics) {
		AffineTransform backupTransform = graphics.getTransform();
		double theta = direction.getAssociatedRotation();
		double tx = position.getCol();
		double ty = position.getRow();
		graphics.translate(tx, ty);
		graphics.rotate(theta, 0.5, 0.5);
		graphics.setColor(color);

		graphics.fill(arrow);

		graphics.setTransform(backupTransform);
	}

	public Ground getGround() {
		return ground;
	}

	public void setPainter(Painter painter) {
		checkNotNull(painter, "painter");
		this.painter = painter;
	}

	/**
	 * Retrieves the mouse position in ground coordinates, or null if mouse is
	 * not over the ground.
	 * 
	 * @return
	 */
	public Position getCurrentMousePosition() {
		Point mousePosition = getMousePosition();
		return mousePosition == null ? null
				: getPositionFromMousePosition(mousePosition);
	}

	private Position getPositionFromMousePosition(Point point) {
		return new Position(point.y / this.tileSize, point.x / this.tileSize);
	}

	public void drawNotificationBesideMouse(String notification,
			Graphics2D graphics) {
		Point2D mousePosition = this.getMousePosition();
		if (mousePosition != null) {
			graphics.setColor(NOTIFICATION_COLOR);
			graphics.setFont(getNotificationFont());

			float x = (float) (mousePosition.getX() / this.tileSize + 0.3f);
			float y = (float) (mousePosition.getY() / this.tileSize - 0.3f);
			graphics.drawString(notification, x, y);
		}
	}

	private Font getNotificationFont() {
		float size = NOTIFICATION_TEXT_SIZE / (float) this.tileSize;
		return new Font(null).deriveFont(Font.BOLD, size);
	}

	public void setElapsedTickTime(double elapsedTickTime) {
		checkArgCondition(elapsedTickTime, 0 <= elapsedTickTime
				&& elapsedTickTime <= 1.0);
		this.elapsedTickTime = elapsedTickTime;
	}
}
