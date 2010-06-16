package view.game;

import static model.utils.ArgumentUtils.checkNotNull;
import static view.game.GroundPainter.TILE_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import model.warehouse.Ground;
import model.warehouse.Position;
import view.game.edition.EditionTool;

public class GroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Ground ground;
	private GroundPainter groundPainter;
	private EditionTool editionTool;

	public GroundPanel(Ground ground) {
		super();

		this.ground = ground;
		this.groundPainter = new GroundPainter(ground);

		/*
		 * TODO Do not hard-code Color.white =(.
		 */
		this.setBackground(Color.WHITE);
		/*
		 * This a scroll-able panel. The scroll can be used in the area.
		 */
		this.setPreferredSize(this.getGroundSize(ground));

		this.setEditionTool(new NullEditionTool());
		this.setMouseListeners();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g2d = (Graphics2D) graphics;
		this.groundPainter.paint(g2d);
		this.editionTool.paint(g2d);
	}

	public void setEditionTool(EditionTool tool) {
		checkNotNull(tool, "edition tool");
		tool.reset();
		if (this.editionTool != null)
			this.editionTool.reset();
		this.editionTool = tool;
	}

	public EditionTool getEditionTool() {
		return this.editionTool;
	}

	public Ground getGround() {
		return ground;
	}

	public GroundPainter getPainter() {
		return this.groundPainter;
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

	private void setMouseListeners() {

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editionTool.mouseClicked(getPositionFromMousePosition(e
						.getPoint()));
			}
		});
	}

	private Dimension getGroundSize(Ground ground) {
		int width = ground.getCols() * TILE_SIZE;
		int height = ground.getRows() * TILE_SIZE;
		return new Dimension(width, height);
	}

	private Position getPositionFromMousePosition(Point point) {
		return new Position(point.y / TILE_SIZE, point.x / TILE_SIZE);
	}

	public void drawNotificationBesideMouse(String notification,
			Graphics2D graphics) {
		Point mousePosition = this.getMousePosition();
		if (mousePosition != null) {
			int x = mousePosition.x + 20;
			int y = mousePosition.y - 20;
			graphics.setColor(Color.BLACK);
			graphics.drawString(notification, x, y);
		}
	}
}

class NullEditionTool extends EditionTool {

	public NullEditionTool() {
		// TODO: this is quite ugly xD
		super(null, null);
	}

	@Override
	public void paint(Graphics2D graphics) {
	}

	@Override
	public void mouseClicked(Position position) {
	}

	@Override
	public void reset() {
	}

}