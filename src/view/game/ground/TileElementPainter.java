package view.game.ground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import model.production.Product;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.Wall;
import view.game.ImageLoader;
import view.game.TileElementImageRecognizer;

/**
 * Visitor used to draw each element in the warehouse.
 */
public class TileElementPainter extends TileElementImageRecognizer {

	private static final double ANIMATION_END_TIME = 0.75;
	private static final double ANIMATION_START_TIME = 0.25;
	private static final double ANIMATION_TOTAL_TIME = ANIMATION_END_TIME
			- ANIMATION_START_TIME;

	private static final Color BROKEN_MACHINE_COLOR = new Color(1, 0, 0, 0.4F);

	private GroundPanel groundPanel;
	private Graphics2D graphics;
	private double elapsedTickTime;

	public TileElementPainter(GroundPanel groundPanel, Graphics2D graphics,
			double elapsedTickTime) {
		this.groundPanel = groundPanel;
		this.graphics = graphics;
		this.elapsedTickTime = elapsedTickTime;
	}

	@Override
	protected void onLineElmentVisited(ProductionLineElement element,
			BufferedImage image) {

		Position pos = element.getPosition();
		groundPanel.drawImage(image, pos.getRow(), pos.getCol(), element
				.getWidth(), element.getHeight(), graphics);
		drawProductOverElement(element);
	}

	@Override
	protected void onWallVisited(Wall wall, BufferedImage image) {
		Position from = wall.getPosition();
		Position to = from.add(new Position(wall.getHeight(), wall.getWidth()));

		for (int row = from.getRow(); row < to.getRow(); row++)
			for (int col = from.getCol(); col < to.getCol(); col++)
				groundPanel.drawImage(image, row, col, 1, 1, graphics);
	}

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		super.visitProductionMachine(machine);
		drawMachineState(graphics, machine);
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		super.visitQualityControlMachine(machine);
		drawMachineState(graphics, machine);
	}

	private void drawMachineState(final Graphics2D graphics, Machine machine) {
		if (machine.isBroken())
			// TODO: draw image instead of ugly rectangle.
			groundPanel.drawRectangle(graphics, machine.getPosition(), machine
					.getWidth(), machine.getHeight(), BROKEN_MACHINE_COLOR);
	}

	private void drawProductOverElement(ProductionLineElement element) {
		Product product = element.getProductContained();
		if (product != null && element.canHaveNextLineElement()) {
			Position startPos;
			if (element.getWidth() == 1 && element.getHeight() == 1)
				startPos = element.getPosition();
			else
				startPos = element.getOutputConnectionPosition().subtract(
						element.getOutputConnectionDirection()
								.getAssociatedPosition());
			Position endPos = element.getOutputConnectionPosition();
			Position posDiff = endPos.subtract(startPos);
			double dt;
			if (this.elapsedTickTime < ANIMATION_START_TIME)
				dt = 0;
			else if (this.elapsedTickTime > ANIMATION_END_TIME)
				dt = 1;
			else
				dt = (this.elapsedTickTime - ANIMATION_START_TIME) / ANIMATION_TOTAL_TIME;

			
			double x = startPos.getCol() + posDiff.getCol() * dt + 0.35;
			double y = startPos.getRow() + posDiff.getRow() * dt + 0.35;
			double size = 0.30;
			Ellipse2D.Double dot = new Ellipse2D.Double(x, y, size, size);
			graphics.setColor(Color.BLACK);
			graphics.fill(dot);
		}
	}
}
