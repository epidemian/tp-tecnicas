package view.game.ground;

import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

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

public class StaticGroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Color HIGHLIGHT_COLOR = new Color(0, 1, 0, 0.3F);
	private static final Color HIGHLIGHT_ARROWS_COLOR = new Color(0, 1, 0, 0.7F);
	private static final Color NOTIFICATION_COLOR = Color.BLACK;

	protected static final int DEFAULT_TILE_SIZE = 50;

	private static final float NOTIFICATION_TEXT_SIZE = 15f;

	private final Ground ground;
	private int tileSize;
	

	public StaticGroundPanel(Ground ground) {
		this(ground, DEFAULT_TILE_SIZE);
	}

	public StaticGroundPanel(Ground ground, int tileSize) {
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

		
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);

		g2d.scale(tileSize, tileSize);

		g2d.setStroke(new BasicStroke(1.0f / this.tileSize));
		drawVerticalLines(g2d);
		drawHorizontalLines(g2d);
		this.ground.visitElements(new TileElementPainter(g2d));
	}

	private void drawVerticalLines(Graphics2D graphics) {
		for (int col = 0; col <= this.ground.getCols(); col++)
			graphics.drawLine(col, 0, col, this.ground.getRows());
	}

	private void drawHorizontalLines(Graphics2D graphics) {
		for (int row = 0; row <= this.ground.getRows(); row++)
			graphics.drawLine(0, row, this.ground.getCols(), row);
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
}
