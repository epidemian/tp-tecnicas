package view.game.ground;

import static view.game.ground.ProductPaintingUtils.*;

import java.awt.Graphics2D;

import model.production.elements.ProductionLineElement;
import model.production.line.ProductionLine;
import model.warehouse.Warehouse;

public class UnpausedProductPainter {

	private Warehouse warehouse;

	public UnpausedProductPainter(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public void paintProducts(Graphics2D graphics,
			double elapsedTickTime) {

		for (ProductionLine line : this.warehouse.getProductionLines()) {
			paintProductionLineProducts(line, graphics, elapsedTickTime);
		}
	}

	private void paintProductionLineProducts(ProductionLine line,
			Graphics2D graphics, double elapsedTickTime) {
		boolean working = line.isWorking();
		for (ProductionLineElement lineElement : line) {
			if (working)
				drawMovingProduct(lineElement, elapsedTickTime, graphics);
			else
				drawStaticProduct(lineElement, graphics);
		}
	}

}
