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
import view.ImageLoader;
import view.game.TileElementImageRecognizer;

/**
 * Visitor used to draw each element in the warehouse.
 */
public class TileElementPainter extends TileElementImageRecognizer {

	private static final String BROKEN_MACHINE_IMG = "./broken-machine.png";

	private Graphics2D graphics;

	public TileElementPainter(Graphics2D graphics) {
		this.graphics = graphics;
	}

	@Override
	protected void onLineElmentVisited(ProductionLineElement element,
			BufferedImage image) {

		Position pos = element.getPosition();
		drawImage(image, pos.getRow(), pos.getCol(), element.getWidth(),
				element.getHeight());
	}

	@Override
	protected void onWallVisited(Wall wall, BufferedImage image) {
		Position from = wall.getPosition();
		Position to = from.add(new Position(wall.getHeight(), wall.getWidth()));

		for (int row = from.getRow(); row < to.getRow(); row++)
			for (int col = from.getCol(); col < to.getCol(); col++)
				drawImage(image, row, col, 1, 1);
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

	public void drawImage(Image img, int row, int col, int width, int height) {
		graphics.drawImage(img, col, row, width, height, null);
	}

	private void drawMachineState(final Graphics2D graphics, Machine machine) {
		if (machine.isBroken()) {
			int offsetRow = machine.getHeight() - 1;
			int offsetCol = machine.getWidth() - 1;
			Position pos = machine.getPosition().add(
					new Position(offsetRow, offsetCol));
			BufferedImage image = ImageLoader.getImage(BROKEN_MACHINE_IMG);
			drawImage(image, pos.getRow(), pos.getCol(), 1, 1);
		}
	}
}
