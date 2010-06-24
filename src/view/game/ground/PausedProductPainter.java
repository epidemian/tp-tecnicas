package view.game.ground;

import static view.game.ground.ProductPaintingUtils.drawMovingProduct;
import java.awt.Graphics2D;

import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.TileElementVisitor;

public class PausedProductPainter {

	

	private Ground ground;

	public PausedProductPainter(Ground ground) {
		this.ground = ground;
	}

	public void paintProducts(final Graphics2D graphics,
			final double elapsedTickTime) {
		// Note: this instance might be cached.
		ground.visitElements(new TileElementVisitor() {
			@Override
			public void visitConveyor(Conveyor conveyor) {
				drawMovingProduct(conveyor, elapsedTickTime, graphics);
			}

			@Override
			public void visitInputProductionLineElement(
					InputProductionLineElement inputLineElement) {
				drawMovingProduct(inputLineElement, elapsedTickTime,
						graphics);
			}

			@Override
			public void visitOutputProductionLineElement(
					OutputProductionLineElement outputLineElement) {
				drawMovingProduct(outputLineElement, elapsedTickTime,
						graphics);
			}

			@Override
			public void visitProductionMachine(ProductionMachine machine) {
				drawMovingProduct(machine, elapsedTickTime, graphics);
			}

			@Override
			public void visitQualityControlMachine(QualityControlMachine machine) {
				drawMovingProduct(machine, elapsedTickTime, graphics);
			}
		});
	}

	

}
