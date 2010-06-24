package view.game.ground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import model.production.Product;
import model.production.elements.ProductionLineElement;
import model.warehouse.Position;

public class ProductPaintingUtils {

	private static final double PRODUCT_DIAMETER = 0.30;
	private static final double MARGIN = (1.0 - PRODUCT_DIAMETER) * 0.5;
	private static final double ANIMATION_END_TIME = 0.75;
	private static final double ANIMATION_START_TIME = 0.25;
	private static final double ANIMATION_TOTAL_TIME = ANIMATION_END_TIME
			- ANIMATION_START_TIME;

	public static void drawMovingProduct(ProductionLineElement element,
			double elapsedTickTime, Graphics2D graphics) {
		Product product = element.getProductContained();
		if (product != null && element.canHaveNextLineElement()) {

			Position startPos = getStartPosition(element);
			Position endPos = element.getOutputConnectionPosition();
			Position posDiff = endPos.subtract(startPos);
			double dt = getRelativeMovement(elapsedTickTime);
			
			double x = startPos.getCol() + posDiff.getCol() * dt + MARGIN;
			double y = startPos.getRow() + posDiff.getRow() * dt + MARGIN;
			drawProduct(x, y, graphics);
		}
	}

	public static void drawStaticProduct(ProductionLineElement element,
			Graphics2D graphics) {
		Product product = element.getProductContained();
		if (product != null) {
			Position position = element.getPosition();
			double x = position.getCol() + MARGIN;
			double y = position.getRow() + MARGIN;
			drawProduct(x, y, graphics);
		}
	}

	private static double getRelativeMovement(double elapsedTickTime) {
		if (elapsedTickTime < ANIMATION_START_TIME)
			return 0;
		else if (elapsedTickTime > ANIMATION_END_TIME)
			return 1;
		else
			return (elapsedTickTime - ANIMATION_START_TIME)
					/ ANIMATION_TOTAL_TIME;
	}

	private static Position getStartPosition(ProductionLineElement element) {
		if (element.getWidth() == 1 && element.getHeight() == 1)
			return element.getPosition();
		else
			return element.getOutputConnectionPosition().subtract(
					element.getOutputConnectionDirection()
							.getAssociatedPosition());
	}

	private static void drawProduct(double x, double y, Graphics2D graphics) {
		Ellipse2D.Double dot = new Ellipse2D.Double(x, y, PRODUCT_DIAMETER,
				PRODUCT_DIAMETER);
		graphics.setColor(Color.BLACK);
		graphics.fill(dot);
	}
}
