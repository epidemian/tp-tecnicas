package view.game.ground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import model.production.Product;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class ProductPainter extends TileElementVisitor {

	private static final double ANIMATION_END_TIME = 0.75;
	private static final double ANIMATION_START_TIME = 0.25;
	private static final double ANIMATION_TOTAL_TIME = ANIMATION_END_TIME
			- ANIMATION_START_TIME;

	private Graphics2D graphics;
	private double elapsedTickTime;

	public ProductPainter(Graphics2D graphics, double elapsedTickTime) {
		this.graphics = graphics;
		this.elapsedTickTime = elapsedTickTime;
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
		drawProductOverElement(conveyor);
	}

	@Override
	public void visitInputProductionLineElement(
			InputProductionLineElement inputLineElement) {
		drawProductOverElement(inputLineElement);
	}

	@Override
	public void visitOutputProductionLineElement(
			OutputProductionLineElement outputLineElement) {
		drawProductOverElement(outputLineElement);
	}

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		drawProductOverElement(machine);
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		drawProductOverElement(machine);
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
				dt = (this.elapsedTickTime - ANIMATION_START_TIME)
						/ ANIMATION_TOTAL_TIME;

			double x = startPos.getCol() + posDiff.getCol() * dt + 0.35;
			double y = startPos.getRow() + posDiff.getRow() * dt + 0.35;
			double size = 0.30;
			Ellipse2D.Double dot = new Ellipse2D.Double(x, y, size, size);
			graphics.setColor(Color.BLACK);
			graphics.fill(dot);
		}
	}

}
